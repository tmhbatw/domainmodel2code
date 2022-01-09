package main;

import main.metainfo.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class ModelCompared {

    public static void main(String[] args) throws Exception {
        System.out.println("start model compare!");
        ModelCompared mc=new ModelCompared();

        XMI2XML b=new XMI2XML();
        Map<String, MyPackage> packageMap1=b.packageInfoMap;
        Map<String, MyClass> classMap1=mc.changeClassNameFormat(b.classInfoMap);

//        System.out.println(packageMap1);
//        System.out.println(classMap1);

        Code2Model a=new Code2Model();
        Map<String,MyPackage> packageMap2 = a.packageInfoMap;
        Map<String,MyClass> classMap2 = mc.changeClassNameFormat(a.classInfoMap);

//        System.out.println(packageMap2);
//        System.out.println(classMap2);

        //packageMap1.remove("Cargo");
        //packageMap2.remove("LocationShare");
        mc.compareStrategyModel("model5",packageMap1,packageMap2);
        mc.compareModel("model6",packageMap1,packageMap2,classMap1,classMap2);
    }

    private void compareModel(String target,Map<String,MyPackage> packageInfoMap,Map<String,MyPackage> packageInfoMap2,
                              Map<String,MyClass> classInfoMap, Map<String,MyClass> classInfoMap2) throws Exception{
        //���ȫ������Ϣ��һ���ļ��У��ļ��ĸ�ʽ������plantumlҪ��ĸ�ʽ�����ɵ��ļ�ͨ��plantUml����չʾ

        File f=new File(target);
        f.createNewFile();

        BufferedWriter bf=new BufferedWriter(new FileWriter(f));

        //plantuml�Ŀ�ʼ�ַ���
        bf.write("@startuml"+"\n");

        //��plantuml���﷨д��model�ļ���

        //���ȸ�ÿһ��java��һ���ض��ķ���
        Map<String,String> packageFlagMap1 = new HashMap<>();
        Map<String,String> classFlagMap1 = new HashMap<>();
        for(String key:packageInfoMap.keySet()){
            String flag = ClassUtil.getRandomFlag();
            packageFlagMap1.put(key,flag);

            for(String className : packageInfoMap.get(key).className){
                classFlagMap1.put(className,flag+"."+className);
            }
        }

        //д�����ģ�͵İ���Ϣ
        for(String packageName:packageInfoMap.keySet()){
            MyPackage cur=packageInfoMap.get(packageName);
            String firstLine = "package "+"\"<<"+getPackageType(cur.annotations)+">>\\n"
                    +packageName+"\""
                    +" as "+packageFlagMap1.get(packageName)+"{";
            bf.write(firstLine+"\n");
            for(String containClassName:cur.className){
                bf.write("\t"+"class "+classFlagMap1.get(containClassName)+"\n");
            }

            bf.write("}"+"\n");
        }

        //���������Ѿ���������˫�������ϵ
        Set<String> existAssociation=new HashSet<>();
        //���ڱ���������ϵ�͹�����ϵ
        Set<String> association = new HashSet<>();
        Set<String> depend = new HashSet<>();

        //д���޽��������ڵ�ÿһ����������ģ��
        for(String className:classInfoMap.keySet()){
            MyClass cur=classInfoMap.get(className);

            bf.write("class "+classFlagMap1.get(className)
                    +" <<"+getClassAnnotationsName(cur.annotations)+">> ##black{"+"\n");

            //����ÿһ�������������չʾ����ӦԪ����ı��
            for(MyAnnotation an:cur.annotations){
                List<MyAnnotationParameter> curList=an.parameters;
                for(MyAnnotationParameter myAnnotationParameter:curList){

                    String type=myAnnotationParameter.name;
                    String value=myAnnotationParameter.value;

                    value=value.split("\\.")[value.split("\\.").length-1];

                    bf.write("\t"+type+":"+value+"\n");
                }
            }

            bf.write("\n__\n");
            //���չʾÿһ����������Ӧ����������
            for(MyField myField:cur.fields){
                bf.write(	"\t"+myField.type+" "+myField.name+"\n");
            }

            bf.write("\n__\n");
            //���չʾÿһ����������������ķ���
            for(MyMethod myMethod:cur.methods){
                bf.write("\t"+myMethod.returnType+" "+myMethod.methodName+"(");
                bf.write(getClassMethodParameters(myMethod.parameters)+")"+"\n");
            }
            bf.write("\n}\n");
        }



        //д��ģ��֮���������ϵ
        for(String className : classInfoMap.keySet()){
            MyClass cur=classInfoMap.get(className);
            //��ģ��֮�����ѭ������ʱ��Ӧ�ý��б���




            for(String dependency:cur.dependency){
                depend.add(className+" "+dependency);
                dependency = classFlagMap1.get(dependency);
                bf.write(classFlagMap1.get(className)+".[#Black].>"+dependency+"\n");
            }
        }

        //д��ģ��֮��Ĺ�����ϵ
        for(String className :classInfoMap.keySet()){
            MyClass cur=classInfoMap.get(className);

            for(String ass:cur.associations){
                //˫����������
                if(classInfoMap.get(ass).associations.contains(className)){
                    //�Ѿ�������˫�������ϵ��������������ϵ
                    String add= getString(ass,className);
                    if(existAssociation.contains(add)) {
                        continue;
                    }

                    existAssociation.add(add);
                    ass = classFlagMap1.get(ass);
                    bf.write(classFlagMap1.get(className)+"-[#Black]-"+ass+"\n");
                }
                //������������
                else{
                    association.add(className+" "+ass);
                    ass = classFlagMap1.get(ass);
                    bf.write(classFlagMap1.get(className)+"-[#Black]->"+ass+"\n");
                }
            }
        }


        //�������ڶԱȵ�ģ��
        Map<String,String> packageFlagMap2=new HashMap<>();
        Map<String,String> classFlagMap2=new HashMap<>();

        for(String packageName : packageInfoMap2.keySet()){
            String flag = ClassUtil.getRandomFlag();
            packageFlagMap2.put(packageName,flag);

            for(String className : packageInfoMap2.get(packageName).className){
                classFlagMap2.put(className,flag+"."+className);
            }
        }
        for(String packageName : packageInfoMap.keySet()){
            packageFlagMap2.putIfAbsent(packageName,ClassUtil.getRandomFlag());
            String flag = packageFlagMap2.get(packageName);

            for(String className:packageInfoMap.get(packageName).className) {
                classFlagMap2.put(className,flag+"."+className);
            }
        }

        //д�����ģ�͵İ���Ϣ
        for(String packageName:packageFlagMap2.keySet()){
            MyPackage cur=packageInfoMap2.containsKey(packageName)?
                    packageInfoMap2.get(packageName):packageInfoMap.get(packageName);

            String color = "White";
            if(!packageInfoMap.containsKey(packageName))
                color = "GreenYellow";
            if(!packageInfoMap2.containsKey(packageName))
                color = "LightPink";

            String firstLine = "package "+"\"<<"+getPackageType(cur.annotations)+">>\\n"
                    +packageName+"\""
                    +" as "+packageFlagMap2.get(packageName)+"#"+color+"{";
            bf.write(firstLine+"\n");
            String flag=packageFlagMap2.get(packageName);
            for(String className:classFlagMap2.keySet()){
                if(classFlagMap2.get(className).startsWith(flag))
                    bf.write("\t"+"class "+classFlagMap2.get(className)+"\n");
            }

            bf.write("}"+"\n");
        }

        //д��Ա�ģ�͵�ÿһ���������
        for(String className:classFlagMap2.keySet()){

            //֤������ɾ���Ķ���
            if(!classInfoMap2.containsKey(className)){
                writeClass2Model(classInfoMap.get(className),bf,classFlagMap2,"Red");
                continue;
            }
            //֤�����������Ķ���
            if(!classInfoMap.containsKey(className)){
                writeClass2Model(classInfoMap.get(className),bf,classFlagMap2,"Green");
                continue;
            }

            //�����������Ҫ��������class���жԱ�
            //������������һ�㲻ͬʱ����Ҫ��������Ϊ��ɫ��
            MyClass myClass1=classInfoMap.get(className);
            MyClass myClass2=classInfoMap2.get(className);
            boolean flag=isSame(myClass1,myClass2);

            String color = flag?"Black":"Blue";

            bf.write("class "+classFlagMap2.get(className)
                    +" <<"+getClassAnnotationsName(myClass2.annotations)+">> ##"+color+"{"+"\n");

            //����ÿһ�������������չʾ����ӦԪ����ı��
            List<MyAnnotationParameter> l2=new ArrayList<>();
            for(MyAnnotation an:myClass2.annotations){
                l2.addAll(an.parameters);
            }
            List<MyAnnotationParameter> l1=new ArrayList<>();
            for(MyAnnotation an:myClass1.annotations) {
                l1.addAll(an.parameters);
            }

            for (MyAnnotationParameter a1 : l2) {
                String add="+";
                for (MyAnnotationParameter a2 : l1) {
                    if (a1.name.equals(a2.name)) {
                        add = "~";
                        if(a1.value.equals(a2.value))
                            add = "";
                        break;
                    }
                }
                bf.write("\t"+add+a1.name+":"+a1.value+"\n");
            }
            for (MyAnnotationParameter a1 : l1) {
                boolean find = false;
                for (MyAnnotationParameter a2 : l2) {
                    if (a1.name.equals(a2.name)) {
                        find = true;
                        break;
                    }
                }
                if(!find)
                    bf.write("\t"+"-"+a1.name+":"+a1.value+"\n");
            }

            bf.write("\n__\n");
            //���չʾÿһ����������Ӧ����������
            for(MyField m1:myClass2.fields){
                String add="+";
                for(MyField m2:myClass1.fields){
                    if(m1.name.equals(m2.name)){
                        add="~";
                        if(m1.type.equals(m2.type))
                            add="";
                        break;
                    }
                }
                bf.write(	"\t"+add+m1.type+" "+m1.name+"\n");
            }
            for(MyField m1:myClass1.fields){
                boolean find =false;
                for(MyField m2:myClass2.fields){
                    if(m1.name.equals(m2.name)){
                        find=true;
                        break;
                    }
                }
                if(!find)
                bf.write(	"\t"+"-"+m1.type+" "+m1.name+"\n");
            }
            bf.write("\n__\n");
            //���չʾÿһ����������������ķ���
            for(MyMethod m1:myClass2.methods){
                String add = "+";
                for(MyMethod m2:myClass1.methods){
                    if(m1.methodName.equals(m2.methodName)){
                        add = "~";
                        if(m1.returnType.equals(m2.returnType)&&getClassMethodParameters(m1.parameters).
                                equals(getClassMethodParameters(m2.parameters)))
                            add = "";
                    }
                }
                bf.write("\t"+add+m1.returnType+" "+m1.methodName+"(");
                bf.write(getClassMethodParameters(m1.parameters)+")"+"\n");
            }
            for(MyMethod m1:myClass1.methods){
                boolean find=false;
                for(MyMethod m2:myClass2.methods){
                    if(m1.methodName.equals(m2.methodName)){
                        find=true;
                        break;
                    }
                }
                if(!find) {
                    bf.write("\t" + "-" + m1.returnType + " " + m1.methodName + "(");
                    bf.write(getClassMethodParameters(m1.parameters) + ")" + "\n");
                }
            }
            bf.write("\n}\n");

        }


        //���������Ѿ���������˫�������ϵ
        Set<String> existAssociation2=new HashSet<>();
        //���ڱ���������ϵ�͹�����ϵ
        Set<String> association2 = new HashSet<>();
        Set<String> depend2 = new HashSet<>();

        //д��ģ��֮���������ϵ
        for(String className : classInfoMap2.keySet()){
            MyClass cur=classInfoMap.get(className);
            //��ģ��֮�����ѭ������ʱ��Ӧ�ý��б���


            for(String dependency:cur.dependency){
                depend2.add(className+" "+dependency);
            }
        }
        //д��Ա�ģ��֮��Ĺ�����ϵ
        for(String className :classInfoMap2.keySet()){
            MyClass cur=classInfoMap.get(className);

            for(String ass:cur.associations){
                //˫����������
                if(classInfoMap2.get(ass).associations.contains(className)){
                    //�Ѿ�������˫�������ϵ��������������ϵ
                    String add= getString(ass,className);
                    existAssociation2.add(add);
                }
                //������������
                else{
                    association2.add(className+" "+ass);
                }
            }
        }
        writeClassRelation("..>",classFlagMap2,depend,depend2,bf);
        writeClassRelation("--",classFlagMap2,existAssociation,existAssociation2,bf);
        writeClassRelation("-->",classFlagMap2,association,association2,bf);
        bf.write("@enduml");

        bf.flush();
        bf.close();
    }

    private void  writeClassRelation(String relation,Map<String,String> class2RealName,
                                      Set<String> l1,Set<String> l2,BufferedWriter bf) throws Exception{
        for(String d:l2){
            String re="";
            if(l1.contains(d)){
                re = writeClassRelation(d,"Black",relation,class2RealName);
            }else{
                re = writeClassRelation(d,"Green",relation,class2RealName);
            }
            bf.write(re+"\n");
        }
        for(String d:l1){
            if(!l2.contains(d))
                bf.write(writeClassRelation(d,"Red",relation,class2RealName)+"\n");
        }
    }

    private String writeClassRelation(String s,String color,String relation,Map<String,String> class2RealName){
        String class1=s.split(" ")[0];
        String class2=s.split(" ")[1];

        relation = relation.charAt(0)+"[#"+color+"]"+relation.substring(1);

        return class2RealName.get(class1)+relation+class2RealName.get(class2);
    }

    private void writeClass2Model(MyClass cur,BufferedWriter bf,Map<String,String> classFlagMap1,String color) throws Exception{
        String className = cur.name;
        bf.write("class "+classFlagMap1.get(className)
                +" <<"+getClassAnnotationsName(cur.annotations)+">> ##"+color+"{"+"\n");

        String add = color.equals("Green")?"+":"-";

        //����ÿһ�������������չʾ����ӦԪ����ı��
        for(MyAnnotation an:cur.annotations){
            List<MyAnnotationParameter> curList=an.parameters;
            for(MyAnnotationParameter myAnnotationParameter:curList){

                String type=myAnnotationParameter.name;
                String value=myAnnotationParameter.value;

                value=value.split("\\.")[value.split("\\.").length-1];

                bf.write("\t"+add+type+":"+value+"\n");
            }
        }

        bf.write("\n__\n");
        //���չʾÿһ����������Ӧ����������
        for(MyField myField:cur.fields){
            bf.write(	"\t"+add+myField.type+" "+myField.name+"\n");
        }

        bf.write("\n__\n");
        //���չʾÿһ����������������ķ���
        for(MyMethod myMethod:cur.methods){
            bf.write("\t"+add+myMethod.returnType+" "+myMethod.methodName+"(");
            bf.write(getClassMethodParameters(myMethod.parameters)+")"+"\n");
        }
        bf.write("\n}\n");
    }


    //�ж�����classInfo���ǲ�����ȫһ��
    private boolean isSame(MyClass myClass1,MyClass myClass2){
        if(!myClass1.name.equals(myClass2.name))
            return false;
        return isSameMyField(myClass1.fields,myClass2.fields)&&
                isSameMyAnnotation(myClass1.annotations,myClass2.annotations)&&
                isSameMyMethod(myClass1.methods,myClass2.methods);
    }

    private boolean isSameMyMethod(List<MyMethod> l1,List<MyMethod> l2){
        if(l1.size()!=l2.size())
            return false;
        Collections.sort(l1,(o1,o2)->{return o1.methodName.compareTo(o2.methodName);});
        Collections.sort(l2,(o1,o2)->{return o1.methodName.compareTo(o2.methodName);});

        for(int i=0;i<l1.size();i++){
            MyMethod a= l1.get(i);
            MyMethod b=l2.get(i);

            if(!a.methodName.equals(b.methodName)||!a.returnType.equals(b.returnType)||
                !isSameMyField(a.parameters,b.parameters))
                return false;
        }
        return true;
    }

    private boolean isSameMyField(List<MyField> l1,List<MyField> l2){
        if(l1.size()!=l2.size())
            return false;

        Collections.sort(l1,(o1,o2)->{return o1.name.compareTo(o2.name);});
        Collections.sort(l2,(o1,o2)->{return o1.name.compareTo(o2.name);});

        for(int i=0;i<l1.size();i++){
            MyField a =l1.get(i);
            MyField b=l2.get(i);
            if(!a.name.equals(b.name)||!a.type.equals(b.type))
                return false;
        }
        return true;
    }

    private boolean isSameMyAnnotation(List<MyAnnotation> l1,List<MyAnnotation> l2){
        if(l1.size()!=l2.size())
            return false;
        Collections.sort(l1,(o1,o2)->{return o1.name.compareTo(o2.name);});
        Collections.sort(l2,(o1,o2)->{return o1.name.compareTo(o2.name);});
        for(int i=0;i<l1.size();i++){
            MyAnnotation a=l1.get(i);
            MyAnnotation b=l2.get(i);

            if(!a.name.equals(b.name)||!isSameMyAnnotationParameter(a.parameters,b.parameters))
                return false;
        }
        return true;
    }

    private boolean isSameMyAnnotationParameter(List<MyAnnotationParameter> l1,List<MyAnnotationParameter> l2){
        if(l1.size()!=l2.size())
            return false;

        Collections.sort(l1,(o1,o2)->{return o1.name.compareTo(o2.name);});
        Collections.sort(l2,(o1,o2)->{return o1.name.compareTo(o2.name);});

        for(int i=0;i<l1.size();i++){
            if(!l1.get(i).name.equals(l2.get(i).name)||!l1.get(i).value.equals(l2.get(i).value))
                return false;
        }
        return true;
    }


    private void compareStrategyModel(String target, Map<String,MyPackage> packageInfoMap,
                                      Map<String,MyPackage> packageInfoMap2) throws Exception{
        File f=new File(target);
        f.createNewFile();

        BufferedWriter bf=new BufferedWriter(new FileWriter(f));

        bf.write("@startuml"+"\n");

        Map<String,String> packageRealNameMap=new HashMap<>();

        for(String packageName : packageInfoMap.keySet()) {
            MyPackage cur=packageInfoMap.get(packageName);
            String realPackageName = "\""+"<<"+getPackageType(cur.annotations)+">>"+"\\n"+packageName+"\"";

            String flag = ClassUtil.getRandomFlag();

            packageRealNameMap.put(packageName,flag);

            String firstLine = "package "+ realPackageName +" as "+flag+ " {";

            bf.write(firstLine+"\n");

            bf.write("}");
            bf.write("\n");
        }

        Map<String,String> packageRealNameMap2=new HashMap<>();

        for(String packageName : packageInfoMap2.keySet()){
            //����ս��ģʽ���޽���������ͬ
            String color = "white";

            //��ɫ��������޽���������������
            if(!packageInfoMap.containsKey(packageName)){
                color = "GreenYellow";
            }

            MyPackage cur=packageInfoMap2.get(packageName);
            String realPackageName = "\""+"<<"+getPackageType(cur.annotations)+">>"+"\\n"+packageName+"\"";

            String flag = ClassUtil.getRandomFlag();
            packageRealNameMap2.put(packageName,flag);

            String firstLine = "package "+ realPackageName +" as "+flag+ "#"+color+" {";

            bf.write(firstLine+"\n");

            bf.write("}");
            bf.write("\n");
        }

        for(String packageName : packageInfoMap.keySet()){
            String color = "LightPink";

            //�������µ��޽��������У�ɾ���������
            if(!packageInfoMap2.containsKey(packageName)){
                MyPackage cur=packageInfoMap.get(packageName);
                String realPackageName = "\""+"<<"+getPackageType(cur.annotations)+">>"+"\\n"+packageName+"\"";

                String flag = ClassUtil.getRandomFlag();
                packageRealNameMap2.put(packageName,flag);


                String firstLine = "package "+ realPackageName +" as "+flag+ "#"+color+" {";

                bf.write(firstLine+"\n");

                bf.write("}");
                bf.write("\n");
            }
        }


        //��������Ѿ�������ϵ���޽�������֮��Ļ���ϵ
        Set<String> partnership=new HashSet<>();

        //��������޽�������֮��������ι�ϵ
        Map<String,String> upDownRelationship = new HashMap();

        //�����޽�������֮��Ĺ�ϵ
        for(String packageName : packageInfoMap.keySet()) {
            MyPackage myPackage=packageInfoMap.get(packageName);
            for(MyAnnotation annotation: myPackage.annotations) {
                //�������Ӧ��ֻ��һ������
                if(annotation.name.equals("Partnership")){
                    String anotherContext=annotation.parameters.get(0).value;

                    String add=getString(packageName,anotherContext);
                    partnership.add(add);

                }

                //������޽���������һ�������޽�������ʱ�������޽������������ι�ϵ
                if(annotation.name.equals("DownStreamContext")){
                    String downStreamContextType = getValueByName("downStreamContextType",annotation.parameters);

                    String upStreamContextType = "Default";
                    String upStreamContextName = getValueByName("upStreamContextName", annotation.parameters);

                    if(packageInfoMap.containsKey(upStreamContextName)){
                        MyPackage myPackage1=packageInfoMap.get(upStreamContextName);
                        for(MyAnnotation myAnnotation:myPackage1.annotations){
                            if(myAnnotation.name.equals("UpStreamContext")){
                                if(getValueByName("downStreamContextName",myAnnotation.parameters).equals(packageName)){
                                    upStreamContextType = getValueByName("upStreamContextType", myAnnotation.parameters);
                                }
                            }
                        }
                    }

                    upDownRelationship.put(packageName+" "+upStreamContextName,downStreamContextType+" "+upStreamContextType);

                }
            }
        }

        Set<String> partnership2=new HashSet<>();
        Map<String,String> upDownRelationship2=new HashMap<>();
        //������һ���޽�������֮��Ĺ�ϵ
        for(String packageName : packageInfoMap2.keySet()) {
            MyPackage myPackage=packageInfoMap2.get(packageName);
            for(MyAnnotation annotation: myPackage.annotations) {
                //�������Ӧ��ֻ��һ������
                if(annotation.name.equals("Partnership")){
                    String anotherContext=annotation.parameters.get(0).value;

                    String add=getString(packageName,anotherContext);
                    partnership2.add(add);

                }

                //������޽���������һ�������޽�������ʱ�������޽������������ι�ϵ
                if(annotation.name.equals("DownStreamContext")){
                    String downStreamContextType = getValueByName("downStreamContextType",annotation.parameters);

                    String upStreamContextType = "Default";
                    String upStreamContextName = getValueByName("upStreamContextName", annotation.parameters);

                    if(packageInfoMap.containsKey(upStreamContextName)){
                        MyPackage myPackage1=packageInfoMap.get(upStreamContextName);
                        for(MyAnnotation myAnnotation:myPackage1.annotations){
                            if(myAnnotation.name.equals("UpStreamContext")){
                                if(getValueByName("downStreamContextName",myAnnotation.parameters).equals(packageName)){
                                    upStreamContextType = getValueByName("upStreamContextType", myAnnotation.parameters);
                                }
                            }
                        }
                    }

                    upDownRelationship2.put(packageName+" "+upStreamContextName,downStreamContextType+" "+upStreamContextType);

                }
            }
        }

        for(String cur:partnership){
            printPartnership(cur,"Black",bf,packageRealNameMap);

            if(!partnership2.contains(cur)){
                printPartnership(cur,"Red",bf,packageRealNameMap2);
            }
        }

        for(String key:upDownRelationship.keySet()){
            printUpDownRelationship(key,upDownRelationship.get(key),"Black",bf,packageRealNameMap);

            if(!upDownRelationship2.containsKey(key)){
                printUpDownRelationship(key,upDownRelationship.get(key),"Red",bf,packageRealNameMap2);
            }
        }

        for(String cur:partnership2){
            if(partnership.contains(cur)){
                printPartnership(cur,"Black",bf,packageRealNameMap2);
            }else{
                printPartnership(cur,"Green",bf,packageRealNameMap2);
            }
        }

        for(String key:upDownRelationship2.keySet()){
            if(upDownRelationship.containsKey(key)){
                if(upDownRelationship.get(key).equals(upDownRelationship2.get(key))){
                    printUpDownRelationship(key,upDownRelationship2.get(key),"Black",bf,packageRealNameMap2);
                }else{
                    printUpDownRelationship(key,upDownRelationship2.get(key),"Blue",bf,packageRealNameMap2);
                }
            }else{
                printUpDownRelationship(key,upDownRelationship2.get(key),"Green",bf,packageRealNameMap2);
            }
        }


        bf.write("@enduml\n");

        bf.flush();
        bf.close();
    }

    public String getClassMethodParameters(List<MyField> list){
        StringBuilder sb=new StringBuilder();
        for(MyField myField:list){
            sb.append(",").append(myField.type).append(" ").append(myField.name);
        }

        if(sb.length()==0){
            return sb.toString();
        }
        return sb.substring(1);
    }

    private void printUpDownRelationship(String key,String value,String color,BufferedWriter bf,
                                         Map<String,String> packageRealNameMap) throws Exception {
        String packageName = key.split(" ")[0];
        String upStreamContextName = key.split(" ")[1];

        String downStreamContextType = value.split(" ")[0];
        String upStreamContextType = value.split(" ")[1];

        bf.write(packageRealNameMap.get(packageName)+"\""+downStreamContextType+"\""+".[#"+color+"].>"+
                "\""+upStreamContextType+"\""+packageRealNameMap.get(upStreamContextName));
        bf.write("\n");
    }

    private void printPartnership(String cur,String color,BufferedWriter bf,
                                  Map<String,String> packageRealNameMap) throws Exception{
        String packageName = cur.split(" ")[0];
        String anotherContext = cur.split(" ")[1];

        bf.write(packageRealNameMap.get(packageName)+"-[#"+color+"]-"+packageRealNameMap.get(anotherContext)+": partnership");

        bf.write("\n");
    }

    private String getString(String a,String b){
        if(a.compareTo(b)<0){
            return a+" "+b;
        }

        return b+" "+a;
    }



    public Map<String,MyClass> changeClassNameFormat(Map<String,MyClass> classMap){
        Map<String,MyClass> result=new HashMap<>();
        for(String className : classMap.keySet()){
            String simpleName = className.split("\\.")[className.split("\\.").length-1];
            MyClass myClass = classMap.get(className);

            myClass.dependency = changeClassNameFormat(myClass.dependency);
            myClass.associations = changeClassNameFormat(myClass.associations);
            result.put(simpleName,myClass);

            for(MyAnnotation myAnnotation:myClass.annotations){
                for(MyAnnotationParameter mp:myAnnotation.parameters){
                    mp.value = mp.value.split("\\.")[mp.value.split("\\.").length-1];
                }
            }

            myClass.name = myClass.name.split("\\.")[myClass.name.split("\\.").length-1];
        }


        return result;
    }

    public String getPackageType(List<MyAnnotation> list){
        for(MyAnnotation an:list){
            switch(an.name){
                case "BoundedContext":
                case "SharedKernel":
                case "Aggregate":
                case "Module":
                    return an.name;
            }
        }

        return "BoundedContext";
    }

    private List<String> changeClassNameFormat(List<String> list){
        List<String> l=new ArrayList<>();
        //ȥ��
        list=new ArrayList<>(new HashSet<>(list));
        for(String cur:list){
            l.add(cur.split("\\.")[cur.split("\\.").length-1]);
        }
        return l;
    }

    private String getValueByName(String name,List<MyAnnotationParameter> list){
        for(MyAnnotationParameter p:list){
            if(p.name.equals(name)){
                return p.value;
            }
        }
        return "";
    }

    public String getClassAnnotationsName(List<MyAnnotation> list){
        StringBuilder sb=new StringBuilder();
        for(MyAnnotation an:list){
            sb.append(",").append(an.name);
        }

        if(sb.length()==0){
            return sb.toString();
        }
        return sb.substring(1);
    }
}
