package main.codeValidation;

import main.metainfo.MyClass;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RepositoryValidate extends DomainObjectValidate{

    public static Set<String> set=new HashSet<>();
    @Override
    public boolean validate(MyClass myclass, Map<String, MyClass> map) {
        if(myclass.annotations.size()!=1){
            System.out.println(Constraint.CONSTRAINT_14+" in "+myclass.name);
            return false;
        }
        String repositoryName = myclass.annotations.get(0).parameters.get(0).value;
        set.add(repositoryName);
        if(!map.containsKey(repositoryName)){
            System.out.println(Constraint.CONSTRAINT_12+" in "+myclass.name);
            return false;
        }

        if(myclass.fields.size()!=0){
            System.out.println(Constraint.CONSTRAINT_13+" in "+myclass.name);
            return false;
        }
        return true;
    }
}
