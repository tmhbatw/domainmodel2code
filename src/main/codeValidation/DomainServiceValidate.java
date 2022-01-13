package main.codeValidation;

import main.metainfo.MyClass;

import java.util.Map;

public class DomainServiceValidate extends DomainObjectValidate{

    @Override
    public boolean validate(MyClass myclass, Map<String, MyClass> map) {
        if(myclass.annotations.size()!=1){
            System.out.println(Constraint.CONSTRAINT_11+" in "+myclass.name);
            return false;
        }
        if(myclass.fields.size()>0){
            System.out.println(Constraint.CONSTRAINT_10+" in "+myclass.name);
            return false;
        }

        return true;
    }
}
