package main;

import main.domainmodeltag.*;
import main.domainmodeltag.Module;
import main.metainfo.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class XMI2XML {

    //首先获取到每一个节点的类型，因为一哥节点可能有多个类型，所以这里设置了一个list
    //同时获取一个model id与实际model名称的对应关系map
    public Map<String,List<String>> map;
    public Map<String,String> id2NameMap;

    //保存领域模型和对应的标记的值的对应关系
    public Map<String, DomainObject> domainObjectMap;
    public Map<String,DomainObject> aggregateMap;

    //保存领域模型和领域模型对应的限界上下文之间的关系
    public Map<String,String> class2PackageMap;

    //保存限界上下文之间的依赖关系
    public Map<String, Set<String>> dependMap;
    //保存限界上下文之间的被依赖关系
    public Map<String,Set<String>> dependedMap;

    //sourceXMLPath用于保存处理文件的路径
    String sourceXMLPath;
    //targetXMLPath用于保存生成的文件的路径
    String targetXMLPath;
    //用来保存全部出现的java类的名称
    public Set<String> classNameSet ;

    //用于保存用户创建的模型中的包信息（用于后续的模型比较功能）
    public Map<String, MyPackage> packageInfoMap;
    //用于保存用户创建的模型中的每一个类的信息(用于后续的模型比较功能）
    public Map<String, MyClass> classInfoMap;

    public XMI2XML(String source,String target) throws Exception {
        this.sourceXMLPath=source;
        this.targetXMLPath=target;

        init();
    }

    public XMI2XML() throws Exception {
        this.sourceXMLPath = "source.xml";
        this.targetXMLPath = "target.xml";

        init();
    }

    private void init() throws Exception {
        this.map = new HashMap<>();
        this.id2NameMap = new HashMap<>();

        this.domainObjectMap = new HashMap<>();
        this.aggregateMap = new HashMap<>();

        this.class2PackageMap = new HashMap<>();

        this.dependMap = new HashMap<>();
        this.dependedMap = new HashMap<>();

        this.packageInfoMap = new HashMap<>();
        this.classInfoMap = new HashMap<>();
        this.classNameSet = new HashSet<>();

        this.executeChange();
    }


    public static void main(String[] args) throws Exception{
        System.out.println("xmi to xml start");
        XMI2XML cur = new XMI2XML();

        System.out.println(cur.packageInfoMap);
        System.out.println(cur.classInfoMap);
        
        System.out.println(cur.packageInfoMap.get("Cargo"));

        Code2Model curr=new Code2Model(cur.packageInfoMap,cur.classInfoMap,new HashSet<>());
        curr.generateStrategyModel("model3");
        curr.generateModel("model4");

    }

    private void getClassInfo() throws Exception{
        getClassAnnotation();

        BufferedReader br=new BufferedReader(new FileReader(new File(targetXMLPath)));

        String line;
        String className = "";
        String propertyName = "";

        List<String> l=new ArrayList<>();

        while((line=br.readLine())!=null){
            if(line.contains("type=\"Class\"")){
                className = getPropertyFromStr(line.indexOf("name"),line);
            }
            if(line.contains("type=\"Property\"")){
                propertyName = getPropertyFromStr(line.indexOf("name"),line);
                line = br.readLine();

                String properType = getPropertyFromStr(line.indexOf("type"),line);
                if(!classInfoMap.containsKey(className)){
                    System.out.println("丢失class！");
                    continue;
                }

                classInfoMap.get(className).fields.add(new MyField(properType,propertyName));
            }
            l.add(line);

        }

        //papyrus绘图工具不支持输入方法的返回类型
        String returnType = "void";
        for(int i=0;i<l.size();i++){
            line=l.get(i);
            if(line.contains("type=\"Class\"")){
                className = getPropertyFromStr(line.indexOf("name"),line);
            }

            if(line.contains("type=\"Operation\"")){
                String methodName = getPropertyFromStr(line.indexOf("name"),line);
                MyMethod myMethod=new MyMethod(returnType,methodName,new ArrayList<>());
                while(i<l.size()-1&&l.get(i+1).contains("type=\"Parameter\"")){
                    line = l.get(++i);
                    String parameterName = getPropertyFromStr(line.indexOf("name"),line);
                    line = l.get(++i);
                    String parameterType = getPropertyFromStr(line.indexOf("type"),line);
                    i++;
                    myMethod.parameters.add(new MyField(parameterType,parameterName));
                    if(!classInfoMap.containsKey(className)){
                        System.out.println("wrong className!");
                        continue;
                    }
                    classInfoMap.get(className).methods.add(myMethod);
                }
            }
        }

        //去重操作
        for(String key:classInfoMap.keySet()){
            MyClass myClass=classInfoMap.get(key);
            myClass.associations = new ArrayList<>(new HashSet<>(myClass.associations));
            myClass.dependency = new ArrayList<>(new HashSet<>(myClass.dependency));
        }
    }



    //执行修改的函数
    //同时抽取classInfo
    public void executeChange() throws Exception {

        //读取模型的xmi源码
        File source=new File(this.sourceXMLPath);
        FileReader f=new FileReader(source);
        BufferedReader bf=new BufferedReader(f);


        String line;
        String packageId="";

        //对模型的xmi文件格式作简单的处理
        //同时获取id与名称的对应关系，以及class与package的对应关系
        List<String> list=new ArrayList<>();
        while((line=bf.readLine())!=null) {
            if(line.trim().startsWith("<ownedEnd")){
                String typeName = getPropertyFromStr(line.indexOf("type",line.indexOf("type")+1),line);
                String space=getSpaceByTime(getSpaceTime(line));

                space+="<className className=\""+typeName+"\"/>";
                list.add(space);
                if(!line.contains("/>")){
                    while(!(line=bf.readLine()).trim().equals("</ownedEnd>")){
                        //System.out.println(line);
                    }
                }
                continue;
            }

            changeXMIStr(line,list);

            getId2NameMap(id2NameMap,line);

            packageId=getCurPackageName(packageId,line);

            if(line.contains("xmi:type=\"uml:Class\"")){
                String classId = getPropertyFromStr(line.indexOf("xmi:id="),line);
                class2PackageMap.put(classId,packageId);
            }

        }

        for(String key:class2PackageMap.keySet()){
            String pid=class2PackageMap.get(key);
            if(pid.equals(key)){
                continue;
            }
            String className = id2NameMap.get(key);
            classInfoMap.put(className,new MyClass(className,new ArrayList<>(),new ArrayList<>(),new ArrayList<>()
                    ,new ArrayList<>(),new ArrayList<>()));
        }


        //提取包与包之间的依赖关系
        //同时提取类与类之间的关系
        String classId = "";
        for(int i=0;i<list.size();i++){
            String l =list.get(i);
            getDependency(l);

            if(l.contains("xmi:type=\"uml:Class\"")) {
                classId = getPropertyFromStr(l.indexOf("xmi:id="), l);
            }

            getAssociation(l,classId);

            if(l.contains("className")&&i<list.size()-1&&list.get(i+1).contains("className")){
                String classId1 = getPropertyFromStr(l.indexOf("className"),l);
                String className1=id2NameMap.get(classId1);
                l=list.get(++i);
                String classId2 = getPropertyFromStr(l.indexOf("className"),l);
                String className2=id2NameMap.get(classId2);

                if(!className1.equals(className2)&&classInfoMap.containsKey(className2)&&classInfoMap.containsKey(className1)){
                    classInfoMap.get(className1).associations.add(className2);
                    classInfoMap.get(className2).associations.add(className1);
                }

                String supplier = id2NameMap.get(class2PackageMap.get(classId1));
                String client = id2NameMap.get(class2PackageMap.get(classId2));
                if(!supplier.equals(client)) {

                    dependMap.putIfAbsent(client, new HashSet<>());
                    dependMap.putIfAbsent(supplier,new HashSet<>());
                    dependMap.get(supplier).add(client);
                    dependMap.get(client).add(supplier);

                    dependedMap.putIfAbsent(supplier, new HashSet<>());
                    dependedMap.get(supplier).add(client);
                    dependedMap.putIfAbsent(client,new HashSet<>());
                    dependedMap.get(client).add(supplier);
                }
            }
        }

//        System.out.println(dependMap);
//        System.out.println(dependedMap);

        //生成的xml文件的文件路劲
        File target=new File(targetXMLPath);
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
                    bw.write(getSpaceByTime(getSpaceTime(s))+"</ownedAttribute>\n");
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
                    String space=getSpaceByTime(getSpaceTime(s));
                    String type=getPropertyFromStr(s.indexOf("type",s.indexOf("type")+1),s);

                    bw.write(space+"<className className=\""+type+"\"/>"+"\n");
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

        getClassInfo();
        getPackageInfo();
    }

    //提取包与包之间的关联关系
    public void getAssociation(String line,String classId){
        if(line.trim().startsWith("<ownedAttribute")&&line.contains("association=")&&line.contains(" type=")){
            String supplier = id2NameMap.get(class2PackageMap.get(getPropertyFromStr(line.indexOf(" type="),line)));
            String client = id2NameMap.get(class2PackageMap.get(classId));

            String supplierClass = id2NameMap.get(getPropertyFromStr(line.indexOf(" type="),line));
            String clientClass = id2NameMap.get(classId);
            if(classInfoMap.containsKey(supplierClass)&&classInfoMap.containsKey(clientClass)){
                classInfoMap.get(clientClass).associations.add(supplierClass);
            }

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
    public void getDependency(String line){

        if(line.contains("xmi:type=\"uml:Dependency\"")){
            //客户端
            String client=id2NameMap.get(class2PackageMap.get(getPropertyFromStr(line.indexOf("client"),line)));
            //被依赖端
            String supplier = id2NameMap.get(class2PackageMap.get(getPropertyFromStr(line.indexOf("supplier"),line)));

            String clientClass = id2NameMap.get(getPropertyFromStr(line.indexOf("client"),line));
            String supplierClass = id2NameMap.get(getPropertyFromStr(line.indexOf("supplier"),line));
            if(classInfoMap.containsKey(clientClass)&&classInfoMap.containsKey(supplierClass)){
                classInfoMap.get(clientClass).dependency.add(supplierClass);
            }

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


    private void getClassAnnotation(){
        for(String key:domainObjectMap.keySet()){
            DomainObject cur=domainObjectMap.get(key);
            String className = id2NameMap.get(key);
            MyClass myClass = classInfoMap.getOrDefault(className,null);
            if(myClass==null)
                continue;
            switch (getClassName(cur)){
                case "Entity":
                    MyAnnotation myAnnotation=new MyAnnotation("Entity",new ArrayList<>());
                    myAnnotation.parameters.add(new MyAnnotationParameter("identifier",((Entity)cur).identifier));
                    myClass.annotations.add(myAnnotation);
                    break;
                case "ValueObject":
                    myAnnotation = new MyAnnotation("ValueObject",new ArrayList<>());
                    myClass.annotations.add(myAnnotation);
                    break;
                case "DomainEvent":
                    myAnnotation = new MyAnnotation("DomainEvent",new ArrayList<>());
                    myAnnotation.parameters.add(new MyAnnotationParameter("identifier",((DomainEvent)cur).identifier));
                    myAnnotation.parameters.add(new MyAnnotationParameter("timestamp",((DomainEvent)cur).timestamp));
                    myAnnotation.parameters.add(new MyAnnotationParameter("publisher",((DomainEvent)cur).publisher));
                    myAnnotation.parameters.add(new MyAnnotationParameter("subscriber",((DomainEvent)cur).subscriber));
                    myClass.annotations.add(myAnnotation);
                    break;
                case "DomainService":
                    myAnnotation = new MyAnnotation("DomainService",new ArrayList<>());
                    myClass.annotations.add(myAnnotation);
                    break;
                case "Repository":
                    myAnnotation = new MyAnnotation("Repository",new ArrayList<>());
                    myAnnotation.parameters.add(new MyAnnotationParameter("accessingDomainObject",((Repository)cur).accessingDomainObject));
                    myClass.annotations.add(myAnnotation);
                    break;
                case "Factory":
                    myAnnotation = new MyAnnotation("Factory",new ArrayList<>());
                    myAnnotation.parameters.add(new MyAnnotationParameter("creatingDomainObject",((Factory)cur).creatingDomainObject));
                    myClass.annotations.add(myAnnotation);
                    break;
            }
        }

        for(String key:aggregateMap.keySet()){
            String className = id2NameMap.get(key);
            DomainObject cur=aggregateMap.get(key);
            MyClass myClass=classInfoMap.get(className);

            switch (getClassName(cur)){
                case "AggregateRoot":
                    MyAnnotation myAnnotation=new MyAnnotation("AggregateRoot",new ArrayList<>());
                    myClass.annotations.add(myAnnotation);
                    break;
                case "AggregatePart":
                    myAnnotation = new MyAnnotation("AggregatePart",new ArrayList<>());
                    myAnnotation.parameters.add(new MyAnnotationParameter
                            ("aggregateRootType",((AggregatePart)cur).aggregateRootType));
                    myClass.annotations.add(myAnnotation);
                    break;
            }
        }
    }



    private void getPackageInfo(){
        for(String key:domainObjectMap.keySet()){
            String packageName = id2NameMap.get(key);
            switch (getClassName(domainObjectMap.get(key))){
                case "BoundedContext":
                    MyPackage myPackage=new MyPackage(id2NameMap.get(key),new ArrayList<>(),new ArrayList<>());
                    List<MyAnnotationParameter> boundedContextList=new ArrayList<>();
                    boundedContextList.add(new MyAnnotationParameter("name",id2NameMap.get(key)));
                    myPackage.annotations.add(new MyAnnotation("BoundedContext",boundedContextList));

                    addMyPackage(myPackage,key);
                    packageInfoMap.put(packageName,myPackage);
                    break;
                case "Aggregate":
                    myPackage=new MyPackage(id2NameMap.get(key),new ArrayList<>(),new ArrayList<>());

                    List<MyAnnotationParameter> aggregateContextList=new ArrayList<>();
                    aggregateContextList.add(new MyAnnotationParameter("name",id2NameMap.get(key)));
                    myPackage.annotations.add(new MyAnnotation("Aggregate",aggregateContextList));

                    addMyPackage(myPackage,key);
                    packageInfoMap.put(packageName,myPackage);
                    break;
                case "Module":
                    myPackage=new MyPackage(id2NameMap.get(key),new ArrayList<>(),new ArrayList<>());

                    List<MyAnnotationParameter> moduleContextList=new ArrayList<>();
                    moduleContextList.add(new MyAnnotationParameter("name",id2NameMap.get(key)));
                    myPackage.annotations.add(new MyAnnotation("Module",moduleContextList));

                    addMyPackage(myPackage,key);
                    packageInfoMap.put(packageName,myPackage);

                    break;
                case "SharedKernel":
                    myPackage=new MyPackage(id2NameMap.get(key),new ArrayList<>(),new ArrayList<>());
                    SharedKernel sk= (SharedKernel) domainObjectMap.get(key);

                    List<MyAnnotationParameter> sharedKernelContextList=new ArrayList<>();
                    sharedKernelContextList.add(new MyAnnotationParameter("name",id2NameMap.get(key)));
                    sharedKernelContextList.add(new MyAnnotationParameter("oneContext",sk.getOneContext()));
                    sharedKernelContextList.add(new MyAnnotationParameter("theOtherContext",sk.getTheOtherContext()));

                    myPackage.annotations.add(new MyAnnotation("SharedKernel",sharedKernelContextList));


                    addMyPackage(myPackage,key);
                    packageInfoMap.put(packageName,myPackage);
                    break;
            }

        }

    }

    private void addMyPackage(MyPackage myPackage, String key){
        //该包所包含的java类
        for(String classId:class2PackageMap.keySet()){
            if(class2PackageMap.get(classId).equals(key)){
                if(classId.equals(key)) {
                    continue;
                }
                myPackage.className.add(id2NameMap.get(classId));
                System.out.println(myPackage.name+" "+id2NameMap.get(classId));
            }
        }

        String packageName = id2NameMap.get(key);
        for(String dependContext:dependMap.getOrDefault(packageName,new HashSet<>())){
            //双向依赖，建立伙伴关系
            if(dependedMap.getOrDefault(packageName,new HashSet<>()).contains(dependContext)){
                MyAnnotation myAnnotation=new MyAnnotation("Partnership",new ArrayList<>());
                myAnnotation.parameters.add(new MyAnnotationParameter("anotherPartnershipContext",dependContext));
                myPackage.annotations.add(myAnnotation);
            }else {
                MyAnnotation myAnnotation = new MyAnnotation("DownStreamContext", new ArrayList<>());
                myAnnotation.parameters.add(new MyAnnotationParameter("upStreamContextName", dependContext));
                myAnnotation.parameters.add(new MyAnnotationParameter("downStreamContextType", "Default"));
                myPackage.annotations.add(myAnnotation);
            }
        }
        for(String dependedContext:dependedMap.getOrDefault(packageName,new HashSet<>())){
            //双向依赖，已经建立过伙伴关系，直接continue
            if(dependMap.getOrDefault(packageName,new HashSet<>()).contains(dependedContext)){
                continue;
            }
            MyAnnotation myAnnotation=new MyAnnotation("UpStreamContext",new ArrayList<>());
            myAnnotation.parameters.add(new MyAnnotationParameter("downStreamContextName",dependedContext));
            myAnnotation.parameters.add(new MyAnnotationParameter("upStreamContextType","Default"));
            myPackage.annotations.add(myAnnotation);
        }
    }




    //更新当前的包名
    public String getCurPackageName(String packageName,String line){

        if(line.contains("xmi:type=\"uml:Package\"")) {
            String newPackageName = getPropertyFromStr(line.indexOf("xmi:id="), line);
            //其中包对应的包是他自己
            class2PackageMap.put(newPackageName,newPackageName);
            return newPackageName;
        }
        return packageName;
    }

    public void getId2NameMap(Map<String,String> map,String s) {
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

    private String getPropertyFromStr(int index,String str) {
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

    private int getTime(String str,String slice) {
        int start=-1;
        int time=0;
        while((start=str.indexOf(slice, start+1))!=-1) {
            time++;
        }
        return time;
    }

    private int getSpaceTime(String line){
        int index=0;
        while(index<line.length()&&line.charAt(index)==' ')
            index++;
        return index;
    }

    private String getSpaceByTime(int time){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<time;i++)
            sb.append(" ");
        return sb.toString();
    }


    //首先对xmi的字符串作简单的处理，提取对象，删除":"符号
    private void  changeXMIStr(String line,List<String> list){

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
    public String changeFormatOfStr(String str,Map<String,List<String>> map) {
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
                    System.out.println("dType wrong format:" +dType);

                    if(!dType.get(0).equals(dType.get(1)))
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
    public String removeStr(String source,String remove) {
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

    private String getClassName(DomainObject cur){
        String name=cur.getClass().getName();
        String[] n=name.split("\\.");

        return n[n.length-1];
    }


}
