package main.codeValidation;

import main.metainfo.MyClass;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FactoryValidate extends DomainObjectValidate{
    public static Set<String> set=new HashSet<>();

    @Override
    public boolean validate(MyClass myclass, Map<String, MyClass> map) {
        if(myclass.annotations.size()!=1){
            System.out.println(Constraint.CONSTRAINT_16+" in "+myclass.name);
            return false;
        }

        String createName = myclass.annotations.get(0).parameters.get(0).value;
        set.add(createName);
        if(!map.containsKey(createName)){
            System.out.println(Constraint.CONSTRAINT_15+" in "+myclass.name);
            return false;
        }

        return true;

    }
}
