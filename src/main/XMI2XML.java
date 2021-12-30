package main;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import main.domainmodeltag.*;
import main.domainmodeltag.Module;

public class XMI2XML {

    //首先获取到每一个节点的类型，因为一哥节点可能有多个类型，所以这里设置了一个list
    //同时获取一个model id与实际model名称的对应关系map
    public static Map<String,List<String>> map=new HashMap<String,List<String>>();
    public static Map<String,String> id2NameMap=new HashMap<String,String>();

    //保存领域模型和对应的标记的值的对应关系
    public static Map<String, DomainObject> domainObjectMap=new HashMap<>();
    public static Map<String,DomainObject> aggregateMap =new HashMap<>();

    //保存领域模型和领域模型对应的限界上下文之间的关系
    public static Map<String,String> class2PackageMap=new HashMap<>();

    //保存限界上下文之间的依赖关系
    public static Map<String, Set<String>> dependMap=new HashMap<>();
    //保存限界上下文之间的被依赖关系
    public static Map<String,Set<String>> dependedMap=new HashMap<>();

    public static void main(String[] args) throws Exception{
        //用于保存处理文件的路径
        String location="source.xml";

        //读取模型的xmi源码
        File source=new File("source.xml");
        FileReader f=new FileReader(source);
        BufferedReader bf=new BufferedReader(f);


        String line;
        String packageId="";

        //对模型的xmi文件格式作简单的处理
        List<String> list=new ArrayList<>();
        while((line=bf.readLine())!=null) {

            changeXMIStr(line,list);

            getId2NameMap(id2NameMap,line);

            packageId=getCurPackageName(packageId,line);

            if(line.contains("xmi:type=\"uml:Class\"")){
                String classId = getPropertyFromStr(line.indexOf("xmi:id="),line);
                class2PackageMap.put(classId,packageId);
            }

        }

        //System.out.println(map);
        for(String key:class2PackageMap.keySet()){
            System.out.println(id2NameMap.get(key)+" belongs to "+id2NameMap.get(class2PackageMap.get(key)));
        }

        //提取包与包之间的依赖关系
        String classId = "";
        for(String l:list){
            getDependency(l);

            if(l.contains("xmi:type=\"uml:Class\"")) {
                classId = getPropertyFromStr(l.indexOf("xmi:id="), l);
            }

            getAssociation(l,classId);
        }

        System.out.println(dependMap);
        System.out.println(dependedMap);

        //生成的xml文件的文件路劲
        File target=new File("target.xml");
        target.createNewFile();

        BufferedWriter bw=new BufferedWriter(new FileWriter(target));

        //生成的xml文件中，其中attribute中的属性必须在""中
        for(int i=0;i<list.size();i++) {
            String s=list.get(i);
            //首先根据xml文件格式的要求，删除xmi根式中的xmi字段
            s = changeFormatOfStr(s,map);

            //在删除了多个字段后，可能出现同一行中存在两个type的情况，这一步主要为了处理这一种情况
            if(getTime(s,"type")==2) {
                if(s.trim().startsWith("<ownedAttribute")&&!s.contains("association")) {
                    //这种情况需要删除第二个type;
                    int secondTypeIndex=s.indexOf("type",s.indexOf("type")+1);
                    String id=getPropertyFromStr(secondTypeIndex,s);
                    String name=id2NameMap.get(id);

                    //删除第一行中的第二个type，把这个type放在第二行中
                    bw.write(s.substring(0,secondTypeIndex-1)+s.substring(secondTypeIndex+7+id.length(),s.length()-2)+">"+"\n");
                    bw.write(getSpaceByTime(getSpaceTime(s)+2)+"<type type=\""+name+"\"/>"+"\n");
                    bw.write(getSpaceByTime(getSpaceTime(s))+"</ownedAttribute>");
                    continue;
                }
                //这种情况下这行直接删除
                if(s.trim().startsWith("<ownedAttribute")&&s.contains("association")){
                    if(s.contains("/>"))
                        continue;
                    while(!list.get(i).trim().equals("</ownedAttribute>"))
                        i++;
                    continue;
                }
                //这种情况下也直接删除
                if(s.trim().startsWith("<ownedEnd")&&s.contains("association")){
                    if(s.contains("/>"))
                        continue;
                    while(!list.get(i).trim().equals("</ownedEnd>"))
                        i++;
                    continue;
                }
            }

            //对于papyrus中的primitiveType的格式作重新的调整
            if(s.contains("type")&&getPropertyFromStr(s.indexOf("type"),s).equals("PrimitiveType")&&s.contains("href")){
                String href=getPropertyFromStr(s.indexOf("href"),s);
                String type=href.split("#")[1];
                if(type.equals("EDate"))
                    type="Date";
                bw.write(getSpaceByTime(getSpaceTime(s))+"<type type=\""+type+"\"/>"+"\n");
                continue;
            }
            bw.write(s+"\n");

            //对于package开始的部分，可能需要增加这个package的上游、下游、以及伙伴关系
            if(s.contains("<packagedElement type=\"Package\"")){
                String packageName = getPropertyFromStr(s.indexOf("name"),s);
                int spaceTime=getSpaceTime(s);

                for(String supplier:dependMap.getOrDefault(packageName,new HashSet<>())){
                    //如果supplier也依赖于该包，则证明这应该是一个伙伴关系
                    if(dependMap.getOrDefault(supplier,new HashSet<>()).contains(packageName)){
                        String add=getSpaceByTime(spaceTime+2);
                        add+="<Partnership ";
                        add+="anotherPartnershipContext=\""+supplier+"\"/>";
                        bw.write(add+"\n");
                    }
                    //不依赖的情况下，证明这个包是一个下游限界上下文
                    else{
                        String add= getSpaceByTime(spaceTime+2);
                        add+="<DownStreamContext ";
                        add+="upStreamContextName=\""+supplier+"\" ";
                        add+="downStreamContextType=\""+"DownStreamContextType.Default\""+"/>";
                        bw.write(add+"\n");
                    }
                }

                for(String client:dependedMap.getOrDefault(packageName,new HashSet<>())){
                    //如果client也被依赖，则跳过这一情况
                    if(dependMap.getOrDefault(packageName,new HashSet<>()).contains(client)){
                        continue;
                    }
                    String add=getSpaceByTime(spaceTime+2);
                    add+="<UpStreamContext ";
                    add+="downStreamContextName=\""+client+"\" ";
                    add+="upStreamContextType=\""+"UpStreamContextType.Default\""+"/>";
                    bw.write(add+"\n");
                }
            }
        }
        bw.flush();

    }

    //提取包与包之间的关联关系
    public static void getAssociation(String line,String classId){
        if(line.trim().startsWith("<ownedAttribute")&&line.contains("association=")&&line.contains(" type=")){
            String supplier = id2NameMap.get(class2PackageMap.get(getPropertyFromStr(line.indexOf(" type="),line)));
            String client = id2NameMap.get(class2PackageMap.get(classId));

            //System.out.println(supplier+" "+client);

            if(!supplier.equals(client)) {

                dependMap.putIfAbsent(client, new HashSet<>());
                dependMap.get(client).add(supplier);

                dependedMap.putIfAbsent(supplier, new HashSet<>());
                dependedMap.get(supplier).add(client);
            }
        }
    }

    //提取包与包之间的依赖关系
    public static void getDependency(String line){

        if(line.contains("xmi:type=\"uml:Dependency\"")){
            //客户端
            String client=id2NameMap.get(class2PackageMap.get(getPropertyFromStr(line.indexOf("client"),line)));
            //被依赖端
            String supplier = id2NameMap.get(class2PackageMap.get(getPropertyFromStr(line.indexOf("supplier"),line)));

            //同一个限界上下文不构成包之间的依赖关系
            if(client.equals(supplier))
                return ;

            dependMap.putIfAbsent(client,new HashSet<>());
            dependMap.get(client).add(supplier);
            dependedMap.putIfAbsent(supplier,new HashSet<>());
            dependedMap.get(supplier).add(client);
            return ;
        }

    }

    //更新当前的包名
    public static String getCurPackageName(String packageName,String line){

        if(line.contains("xmi:type=\"uml:Package\"")) {
            String newPackageName = getPropertyFromStr(line.indexOf("xmi:id="), line);
            //其中包对应的包是他自己
            class2PackageMap.put(newPackageName,newPackageName);
            return newPackageName;
        }
        return packageName;
    }

    public static void getId2NameMap(Map<String,String> map,String s) {
        if(s.contains("xmi:id=")&&s.contains("name=")) {
            int idIndex=s.indexOf("xmi:id");
            String id=getPropertyFromStr(idIndex,s);

            int nameIndex=s.indexOf("name=");
            String name=getPropertyFromStr(nameIndex,s);

            if(id.length()>0&&name.length()>0) {
                map.put(id, name);
            }
        }
    }

    private static String getPropertyFromStr(int index,String str) {
        int time=0;
        String res="";

        while(time<2) {
            if(str.charAt(index)=='"')
                time++;
            else if(time==1)
                res=res+str.charAt(index);
            index++;
        }

        return res;
    }

    private static int getTime(String str,String slice) {
        int start=-1;
        int time=0;
        while((start=str.indexOf(slice, start+1))!=-1) {
            time++;
        }
        return time;
    }

    private static int getSpaceTime(String line){
        int index=0;
        while(index<line.length()&&line.charAt(index)==' ')
            index++;
        return index;
    }

    private static String getSpaceByTime(int time){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<time;i++)
            sb.append(" ");
        return sb.toString();
    }


    //首先对xmi的字符串作简单的处理，提取对象，删除":"符号
    private static void  changeXMIStr(String line,List<String> list){

        //xml中不保留的xmi文件的字符串
        if(line.contains("xmi:version=")||line.contains("</xmi:XMI>"))
            return;

        if(line.contains("metamodel:")) {
            String type="";
            String id="";

            for(String s:line.split(" ")) {
                if(s.contains("metamodel:")) {
                    type=s.split(":")[1];
                }
                if(s.contains("base_Class=")) {
                    id=s.split("=")[1];
                    id=id.substring(1,id.length()-3);
                }
                if(s.contains("base_Package=")){
                    id=s.split("=")[1];
                    id=id.substring(1,id.length()-3);
                }

            }
            if(id.length()>0&&type.length()>0) {
                map.putIfAbsent(id, new ArrayList<>());
                map.get(id).add(type);
                //System.out.println(type+" "+id);
                //存储每个构造性的标记值
                DomainObject o;
                switch (type){
                    case "Entity":
                        String identifier=getPropertyFromStr(line.indexOf("identifier"),line);
                        o=new Entity(identifier);
                        domainObjectMap.put(id,o);
                        break;
                    case "ValueObject":
                        o=new ValueObject();
                        domainObjectMap.put(id,o);
                        break;
                    case "DomainEvent":
                        identifier=getPropertyFromStr(line.indexOf("identifier"),line);
                        String timestamp=getPropertyFromStr(line.indexOf("timestamp"),line);
                        String publisher=getPropertyFromStr(line.indexOf("publisher"),line);
                        String subscriber=getPropertyFromStr(line.indexOf("subscriber"),line);
                        o=new DomainEvent(identifier,timestamp,publisher,subscriber);
                        domainObjectMap.put(id,o);
                        break;
                    case "DomainService":
                        o=new DomainService();
                        domainObjectMap.put(id,o);
                        break;
                    case "Repository":
                    	System.out.println(line);
                        String accessingDomainObject=getPropertyFromStr(line.indexOf("accessingDomainObject"),line);
                        o=new Repository(id2NameMap.get(accessingDomainObject));
                        domainObjectMap.put(id,o);
                        break;
                    case "Factory":
                        String creatingDomainObject=getPropertyFromStr(line.indexOf("creatingDomainObject"),line);
                        o=new Factory(id2NameMap.get(creatingDomainObject));
                        domainObjectMap.put(id,o);
                        break;
                    case "AggregatePart":
                        String aggregateRootType=getPropertyFromStr(line.indexOf("aggregateRootType"),line);
                        o=new AggregatePart(id2NameMap.get(aggregateRootType));
                        aggregateMap.put(id,o);
                        break;
                    case "AggregateRoot":
                        o=new AggregateRoot();
                        aggregateMap.put(id,o);
                        break;
                    //这种情况对应于package元素是一个package时
                    case "BoundedContext":
                        o=new BoundedContext();
                        domainObjectMap.put(id,o);
                        break;
                    case "Aggregate":
                        o=new Aggregate();
                        domainObjectMap.put(id,o);
                        break;
                    case "Module":
                        o=new Module();
                        domainObjectMap.put(id,o);
                        break;
                    case "SharedKernel":
                        String oneContext=getPropertyFromStr(line.indexOf("oneContext"),line);
                        String anotherContext=getPropertyFromStr(line.indexOf("theOtherContext"),line);
                        oneContext=id2NameMap.get(oneContext);
                        anotherContext=id2NameMap.get(anotherContext);
                        o=new SharedKernel(oneContext,anotherContext);
                        domainObjectMap.put(id,o);
                        break;
                    default:
                        System.out.println("error!"+type+" ");
                        break;
                }
            }
            return ;
        }

        list.add(line);
        return ;
    }


    //这一方法用于调整输出的xml文件格式
    public static String changeFormatOfStr(String str,Map<String,List<String>> map) {
        str=removeStr(str,"xmi:");
        str=removeStr(str,"uml:");

        //给element添加DDD type,当领域模型存在多个DDD type时，代码可能需要修改
        if(str.contains("id=")) {
            String id="";
            List<String> dType=new ArrayList<>();
            for(String s:str.split(" ")) {
                if(s.contains("id=")) {
                    id=s.split("=")[1];
                    id=id.substring(1,id.length()-1);
                    dType.addAll(map.getOrDefault(id, new ArrayList<>()));
                }
            }
            if(dType.size()==1) {
                String s=dType.get(0);
                if(str.charAt(str.length()-2)!='/')
                    return str.substring(0,str.length()-1)+" dtype=\""+s+"\" "+domainObjectMap.get(id)+" annotion=\"\">";
                return str.substring(0,str.length()-2)+" dtype=\" "+domainObjectMap.get(id)+s+"\""+" annotion=\"\"/>";
            }else if (dType.size()==2) {
                Collections.sort(dType);

                //这种情况说明dType格式有问题
                if(!dType.get(0).startsWith("Aggregate")||dType.get(1).startsWith("Aggregate")) {
                    System.out.println("dType 格式有误");
                    return "";
                }

                String add =  " dtype=\""+dType.get(1)+"\" "+domainObjectMap.get(id)+" annotion=\"@"+dType.get(0)+aggregateMap.get(id)+"\"";
                if(str.charAt(str.length()-2)!='/')
                    return str.substring(0,str.length()-1)+add+">";

                return str.substring(0,str.length()-2)+add+"/>";
            }
        }

        return str;
    }


    //这一个方法用于移除xmi文件中不必要的字符串前缀
    public static String removeStr(String source,String remove) {
        int removeLength=remove.length();
        int start=-removeLength;


        StringBuilder result=new StringBuilder();
        int pre=0;

        while((start=source.indexOf(remove, start+removeLength))!=-1) {
            result.append(source.substring(pre, start));
            pre=start+removeLength;
        }

        result.append(source.substring(pre));

        return result.toString();
    }

}
