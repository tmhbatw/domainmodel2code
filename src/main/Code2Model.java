package main;

import main.metainfo.*;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.List;

public class Code2Model {

    public static void main(String[] args) throws Exception{
        System.out.println("Code to model start");
        new Code2Model().generateStrategyModel("model2");
        new Code2Model().generateModel("model1");

        Code2Model cur = new Code2Model();
        System.out.println(cur.getPackageInfo().get("Cargo"));
        System.out.println(cur.getClassInfo());
    }

    //���ڽ�������Ŀ·��,Ĭ��Ϊ"main.gen.model";
    private String packagePath = "main.gen.model";

    //��������java������Ϣ
    public Map<String, MyPackage> packageInfoMap;

    //��������ȫ�����ֵ�java�������
    public Set<String> classNameSet ;
    //����ÿһ��java�����Ϣ
    public Map<String, MyClass> classInfoMap ;

    public Code2Model(Map<String,MyPackage> packageInfoMap, Map<String,MyClass> classInfoMap, Set<String> classNameSet){
        this.packageInfoMap = packageInfoMap;
        this.classInfoMap = classInfoMap;
        this.classNameSet = classNameSet;
    }

    public Code2Model() throws ClassNotFoundException {
        this.packageInfoMap = new HashMap<>();
        this.classNameSet = new HashSet<>();
        this.classInfoMap = new HashMap<>();

        init();
    }


    public Code2Model(String packagePath) throws ClassNotFoundException {
        this.packagePath = packagePath;
        this.packageInfoMap = new HashMap<>();
        this.classNameSet = new HashSet<>();
        this.classInfoMap = new HashMap<>();

        init();
    }

    private void init() throws ClassNotFoundException {
        List<Class<?>> classes=ClassUtil.getClasses(packagePath);

        //��ȡ����������������java���Լ�����ע��
        getPackageInfo(classes);

        //��ȡ�����Ϣ
        for(String packageName:packageInfoMap.keySet()){
            //System.out.println(packageInfoMap.get(packageName));
            for(String className : packageInfoMap.get(packageName).className){
                String realName = getRealPath(packageName,className);
                classInfoMap.put(realName,getClassInfo(realName));
            }
        }

    }


    public void generateStrategyModel(String target) throws Exception {
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

            //���ڹ����ں���˵����Ҫ�������ں�һ����ǩ�����������ں�
            for(MyAnnotation myAnnotation:cur.annotations) {
                if(myAnnotation.name.equals("SharedKernel")){
                    bf.write("note top of "+flag+"\n");
                    for(MyAnnotationParameter mp:myAnnotation.parameters){
                        if(mp.name.equals("name")){
                            continue;
                        }
                        bf.write(mp.name+" : "+mp.value+"\n");
                    }
                    bf.write("end note"+"\n");
                }
            }
        }

        //��������Ѿ�������ϵ���޽�������֮��Ļ���ϵ
        Set<String> existPartnership=new HashSet<>();
        //�����޽�������֮��Ĺ�ϵ
        for(String packageName : packageInfoMap.keySet()) {
            MyPackage myPackage=packageInfoMap.get(packageName);
            for(MyAnnotation annotation: myPackage.annotations) {
                //�������Ӧ��ֻ��һ������
                if(annotation.name.equals("Partnership")){
                    String anotherContext=annotation.parameters.get(0).value;
                    //System.out.println(packageName+" "+anotherContext);
                    if(existPartnership.contains(packageName+anotherContext)||
                        existPartnership.contains(anotherContext+packageName))
                        continue;

                    existPartnership.add(packageName+anotherContext);
                    existPartnership.add(anotherContext+packageName);
                    bf.write(packageRealNameMap.get(packageName)+"-[#Black]-"+packageRealNameMap.get(anotherContext)+": partnership");

                    bf.write("\n");
                }

                //System.out.println(annotation);
                //������޽���������һ�������޽�������ʱ�������޽������������ι�ϵ
                if(annotation.name.equals("DownStreamContext")){
                    String downStreamContextType = getValueByName("downStreamContextType",annotation.parameters);

                    String upStreamContextType = "Default";
                    String upStreamContextName = getValueByName("upStreamContextName", annotation.parameters);
                    System.out.println(annotation.parameters);
                    System.out.println(upStreamContextName);

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
                    
                    System.out.println(packageName+" "+upStreamContextName);

                    bf.write(packageRealNameMap.get(packageName)+"\""+downStreamContextType+"\""+".[#Black].>"+
                            "\""+upStreamContextType+"\""+packageRealNameMap.get(upStreamContextName));
                    bf.write("\n");
                }
            }
        }
        System.out.println(packageRealNameMap);


        bf.write("@enduml\n");

        bf.flush();
        bf.close();
    }


    private String getValueByName(String name,List<MyAnnotationParameter> list){
        for(MyAnnotationParameter p:list){
            if(p.name.equals(name)){
                return p.value;
            }
        }
        return "";
    }


    public void generateModel(String target) throws IOException {
        //���ȫ������Ϣ��һ���ļ��У��ļ��ĸ�ʽ������plantumlҪ��ĸ�ʽ�����ɵ��ļ�ͨ��plantUml����չʾ
        String targetLocation = target;

        File f=new File(targetLocation);
        f.createNewFile();

        BufferedWriter bf=new BufferedWriter(new FileWriter(f));

        //plantuml�Ŀ�ʼ�ַ���
        bf.write("@startuml"+"\n");

        //��plantuml���﷨д��model�ļ���

        //д���޽������ĵ�ģ��
        for(String packageName:packageInfoMap.keySet()){
            MyPackage cur=packageInfoMap.get(packageName);
            String firstLine = "package "+"\"<<"+getPackageType(cur.annotations)+">>\\n"
                    +cur.name+"\""
                    +" {";
            bf.write(firstLine+"\n");
            for(String containClassName:cur.className){
                bf.write("\t"+"class "+containClassName+"\n");
            }

            bf.write("}"+"\n");
        }

        //д���޽��������ڵ�ÿһ����������ģ��
        for(String className:classInfoMap.keySet()){
            MyClass cur=classInfoMap.get(className);

            String simpleName = className.split("\\.")[className.split("\\.").length-1];

            bf.write("class "+simpleName+" <<"+getClassAnnotationsName(cur.annotations)+">> ##black{"+"\n");

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
            String simpleName = className.split("\\.")[className.split("\\.").length-1];

            //��ģ��֮�����ѭ������ʱ��Ӧ�ý��б���





            for(String dependency:cur.dependency){
                dependency = dependency.split("\\.")[dependency.split("\\.").length-1];
                bf.write(simpleName+".[#Black].>"+dependency+"\n");
            }
        }

        //д��ģ��֮���������ϵ

        //���������Ѿ���������˫�������ϵ
        Set<String> existAssociation=new HashSet<>();
        for(String className :classInfoMap.keySet()){
            MyClass cur=classInfoMap.get(className);
            String simpleName = className.split("\\.")[className.split("\\.").length-1];

            for(String ass:cur.associations){
                //˫����������
                if(classInfoMap.get(ass).associations.contains(className)){
                    //�Ѿ�������˫�������ϵ��������������ϵ
                    if(existAssociation.contains(ass+className)) {
                        continue;
                    }

                    existAssociation.add(className+ass);
                    ass = ass.split("\\.")[ass.split("\\.").length-1];
                    bf.write(simpleName+"-[#Black]-"+ass+"\n");
                }
                //������������
                else{
                    ass = ass.split("\\.")[ass.split("\\.").length-1];
                    bf.write(simpleName+"-[#Black]->"+ass+"\n");
                }
            }
        }

        bf.write("@enduml");

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

    public MyAnnotation getPackageAnnotation(List<MyAnnotation> list) {
        for(MyAnnotation an:list){
            switch(an.name){
                case "BoundedContext":
                case "SharedKernel":
                case "Aggregate":
                case "Module":
                    return an;
            }
        }

        return null;
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

    private MyClass getClassInfo(String realPath) throws ClassNotFoundException {
        Class<?> curClass = Class.forName(realPath);

        Set<String> association=new HashSet<>();
        Set<String> dependency=new HashSet<>();

        List<MyAnnotation> annotations=new ArrayList<>();
        List<MyField> fields = new ArrayList<>();
        List<MyMethod> methods=new ArrayList<>();

        Set<String> fieldsName= new HashSet<>();

        //���Ȼ�ȡ���ע��
        for(Annotation an:curClass.getAnnotations()){
            //System.out.println(an);
            annotations.addAll(reverseAnnotation(an));
        }

        //��ȡ�������
        for(Field f:curClass.getDeclaredFields()){
            String[] s=f.toString().split(" ");
            //System.out.println(Arrays.toString(s));

            //�п��ܴ������������һ��list����������������Ҫ���⴦��








            int startIndex=0;
            if(s[startIndex].equals("private")||s[startIndex].equals("public")||s[startIndex].equals("protected")){
                startIndex++;
            }

            //˵����ǰ�����������Ϊ�Լ������ԣ������˹�����ϵ
            if(classNameSet.contains(s[startIndex])){
                association.add(s[startIndex]);
            }

            //���ԡ�������ֻ�����һ����
            String[] fieldType=s[startIndex].split("\\.");
            String t=fieldType[fieldType.length-1];

            String[] fieldName=s[startIndex+1].split("\\.");
            String n=fieldName[fieldName.length-1];
            fieldsName.add(n);

            fields.add(new MyField(t,n));
        }

        //��ȡ��ǰ��ķ���
        for(Method m:curClass.getDeclaredMethods()){
            String methodName=m.getName();
            //���ȹ��˵�ϵͳ�Զ����ɵ�getter�Լ�setter�������ڽ���в���չʾ

            if(methodName.length()>3&&(methodName.startsWith("get")||methodName.startsWith("set"))){
                String left=toLowerCase(methodName.charAt(3))+methodName.substring(4);
                if(fieldsName.contains(left))
                    continue;
            }

            String[] rType=m.getReturnType().toString().split(" ");
            String returnType=rType[rType.length-1];

            //�����������

            //�п��ܳ�����������һ��list��������������Ҳ��Ҫ���⴦��










            if(classNameSet.contains(returnType)){
                dependency.add(returnType);
            }

            returnType=returnType.split("\\.")[returnType.split("\\.").length-1];

            List<MyField> parameters=new ArrayList<>();
            //��ȡ������ȫ������
            for(Parameter p:m.getParameters()){
                String parameterType=p.getType().getName();
                if(classNameSet.contains(parameterType)){
                    dependency.add(parameterType);
                }

                parameterType=parameterType.split("\\.")[parameterType.split("\\.").length-1];
                String parameterName=p.getName();

                parameters.add(new MyField(parameterType,parameterName));
            }

            methods.add(new MyMethod(returnType,methodName,parameters));
        }

        MyClass result=new MyClass(realPath,fields,methods,annotations,new ArrayList<>(association),
                new ArrayList<>(dependency));

        return result;
    }

    private static char toLowerCase(char cur){
        if(cur<='Z'&&cur>='A'){
            return (char)(cur-'A'+'a');
        }

        return cur;
    }

    private Field[] getFieldFromClass(String className) throws Exception {
        Class<?> curClass=Class.forName(className);

        java.lang.reflect.Field[] f=curClass.getDeclaredFields();
        for(java.lang.reflect.Field curField:f){
            String[] s=curField.toString().split(" ");

            String[] type=s[s.length-2].split("");
        }

        Field[] result=new Field[f.length];
        return result;
    }


    private String getRealPath(String packageName,String className){
        return packagePath+"."+packageName+"."+className;
    }

    public void getPackageInfo(List<Class<?>> classes) throws ClassNotFoundException {
        //�����ǰClass������ȫ������
        for(Class<?> c:classes){
            Class<?> cur= Class.forName(c.getName());

            String[] name = cur.getName().split("\\.");
            String packageName=name[name.length-2];
            String className=name[name.length-1];

            packageInfoMap.putIfAbsent(packageName,new MyPackage(packageName,new ArrayList<>(),new ArrayList<>()));

            //���������Ϊpackage-infoʱ��֤������ౣ����ǰ�����Ϣ
            //ʵ�ʽ�����ʱ�������Ҫ��ע�⵱��һ���ַ������н���
            if(className.equals("package-info")){
                Annotation[] annotations=cur.getAnnotations();

                for(Annotation an:annotations){
                    packageInfoMap.get(packageName).annotations.addAll(reverseAnnotation(an));
                }
            }

            //�������class������package��
            else{
//                package2ClassMap.putIfAbsent(packageName,new ArrayList<>());
//                package2ClassMap.get(packageName).add(cur.getName());
                packageInfoMap.get(packageName).className.add(className);
                classNameSet.add(cur.getName());
            }
        }

//        System.out.println(classNameSet);
//        System.out.println(package2ClassMap);
    }

    public List<MyAnnotation> reverseAnnotation(Annotation an){
        List<MyAnnotation> result=new ArrayList<>();

        String s = an.toString();
        //System.out.println(s);
        //�������˵��һ��ע������˶�������/�����޽������ĵ���Ϣ����Ҫ�������д���
        if(s.contains("value=")){
            int startIndex=s.indexOf("[");
            int endIndex=s.indexOf("]");

            //System.out.println(s.substring(startIndex+1,endIndex));
            for(String cur:s.substring(startIndex+1,endIndex).split("@")){
                if(cur.length()<=1)
                    continue;
                result.add(reverseAnnotation(cur));
            }
        }else{
            result.add(reverseAnnotation(s));
        }

        return result;
    }

    public MyAnnotation reverseAnnotation(String an) {
        //��ȡע���parameter
        int startIndex = an.indexOf("(");
        int endIndex = an.indexOf(")");

        String[] annotationType = an.substring(0,startIndex).split("\\.");
        String name = annotationType[annotationType.length - 1];

        List<MyAnnotationParameter> parameters=new ArrayList<>();
        String parameter=an.substring(startIndex+1,endIndex);

        //�������ע�ⲻ�����κ����Ե����
        if(parameter.length()==0) {
            return new MyAnnotation(name, parameters);
        }

        String[] para=parameter.split(",");

        for(String p:para){
            System.out.println(p);
            String[] pa=p.trim().split("=");
            if(pa[1].startsWith("class ")){
                pa[1]=pa[1].substring(6);
            }
            parameters.add(new MyAnnotationParameter(pa[0],pa[1]));
        }

        return new MyAnnotation(name,parameters);
    }



    public Map<String,MyPackage> getPackageInfo() throws ClassNotFoundException {

        return this.packageInfoMap;
    }

    public Map<String,MyClass> getClassInfo() throws ClassNotFoundException {

        return this.classInfoMap;
    }


    public void getCodeInformation() throws Exception {
        List<Class<?>> classes=ClassUtil.getClasses(packagePath);

        //��ȡ����������������java���Լ�����ע��
        getPackageInfo(classes);

        //��ȡ�����Ϣ
        for(String packageName:packageInfoMap.keySet()){
            //System.out.println(packageInfoMap.get(packageName));
            for(String className : packageInfoMap.get(packageName).className){
                String realName = getRealPath(packageName,className);
                classInfoMap.put(realName,getClassInfo(realName));
            }
        }
        //System.out.println(packageInfoMap);
        //System.out.println(classInfoMap.get(packagePath+".Cargo.Cargo"));
    }


}
