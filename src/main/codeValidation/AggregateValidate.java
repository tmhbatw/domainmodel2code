package main.codeValidation;

import main.metainfo.MyAnnotation;
import main.metainfo.MyPackage;

import java.util.Map;

public class AggregateValidate {

    public boolean validate(Map<String,MyPackage> myPackage, Map<String,String> map){
        for(String packageName :myPackage.keySet()){
            boolean isAggregate=false;
            for(MyAnnotation cur:myPackage.get(packageName).annotations){
                if(cur.name.equals("Aggregate")){
                    isAggregate=true;
                    break;
                }
            }

            if(isAggregate){
                int rootNumber = 0;
                for(String className : myPackage.get(packageName).className){
                    if(!map.containsKey(className)){
                        System.out.println(Constraint.CONSTRAINT_22+" in "+packageName);
                        return false;
                    }

                    if(map.get(className).equals("AggregateRoot")){
                        rootNumber ++;
                    }
                }

                if(rootNumber!=0){
                    System.out.println(Constraint.CONSTRAINT_21+" in "+packageName);
                    return false;
                }
            }
        }

        return true;
    }
}
