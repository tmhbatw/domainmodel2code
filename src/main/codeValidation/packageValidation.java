package main.codeValidation;

import main.metainfo.MyAnnotation;
import main.metainfo.MyPackage;

import java.util.Map;

public class packageValidation {

    public boolean packageValidate(Map<String, MyPackage> map){
        for(String packageName : map.keySet()){
            MyPackage myPackage = map.get(packageName);

            //判断是否含有包注解
            boolean flag=false;
            for(MyAnnotation an: myPackage.annotations){
                switch (an.name) {
                    case "BoundedContext":
                    case "Aggregate":
                        flag = true;
                        if(!map.containsKey(Util.getParameterFromList(an.parameters,"name"))){
                            System.out.println("name not found err in "+packageName);
                        }
                        break;
                    case "SharedKernel":
                        flag = true;
                        if (!map.containsKey(Util.getParameterFromList(an.parameters, "oneContextName"))
                                || !map.containsKey(Util.getParameterFromList(an.parameters, "theOtherContextName"))
                                || !map.containsKey(Util.getParameterFromList(an.parameters, "name"))) {
                            System.out.println("sharedkernel name not found err in " + packageName);
                            return false;
                        }
                        break;
                    //判断所依赖的上下游限界上下文是否存在
                    case "DownStreamContext":
                        if (!map.containsKey(Util.getParameterFromList(an.parameters, "upStreamContextName"))) {
                            System.out.println("upStreamContextName not found err! in " + packageName);
                            return false;
                        }
                        break;
                    case "UpStreamContext":
                        if (!map.containsKey(Util.getParameterFromList(an.parameters, "downStreamContextName"))) {
                            System.out.println("upStreamContextName not found err! in " + packageName);
                            return false;
                        }
                        break;
                    case "Partnership":
                        if (!map.containsKey(Util.getParameterFromList(an.parameters, "anotherPartnershipContext"))) {
                            System.out.println("anotherPartnershipContext not found err! in " + packageName);
                            return false;
                        }
                        break;
                }
            }
            if(!flag) {
                System.out.println("package " +packageName+" lack package annotation!");
                return false;
            }

        }
        return true;
    }
}
