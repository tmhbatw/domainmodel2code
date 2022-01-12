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
                if (an.name.equals("BoundedContext")
                        || an.name.equals("Aggregate")
                        || an.name.equals("SharedKernel")) {
                    flag = true;
                }
                //判断所依赖的上下游限界上下文是否存在
                else{
                    System.out.println(an);
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
