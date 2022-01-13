package main.codeValidation;

import main.metainfo.MyAnnotation;
import main.metainfo.MyAnnotationParameter;
import main.metainfo.MyClass;
import main.metainfo.MyField;

import java.util.Map;

public class ValueObjectValidate extends DomainObjectValidate{
    @Override
    public boolean validate(MyClass myclass, Map<String,MyClass> map) {
        for(MyAnnotation cur: myclass.annotations){
            for(MyAnnotationParameter para: cur.parameters){
                if(para.name.equals("identifier")){
                    System.out.println(Constraint.CONSTRAINT_03+" in "+myclass.name);
                    return false;
                }
            }
        }

        for(MyField field:myclass.fields){
            if(!field.modifier.equals("private")){
                System.out.println(Constraint.CONSTRAINT_04+" in "+myclass.name);
                return false;
            }
        }

        return true;
    }
}
