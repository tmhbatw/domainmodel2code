package main.codeValidation;

import main.metainfo.MyAnnotation;
import main.metainfo.MyAnnotationParameter;
import main.metainfo.MyClass;
import main.metainfo.MyField;

import java.util.List;
import java.util.Map;

public class EntityValidate extends DomainObjectValidate {

    @Override
    public boolean validate(MyClass myclass, Map<String,MyClass> map) {
        for(MyAnnotation cur:myclass.annotations){
            if(cur.name.equals("Entity")){
                List<MyAnnotationParameter> list=cur.parameters;

                boolean containsIdentifier = false;
                for(MyAnnotationParameter para:list){
                    if(para.name.equals("identifier")){
                        containsIdentifier = true;
                        String[] field = para.value.split(" ");

                        for(String identifier:field) {
                            boolean containIdentifier = false;
                            for (MyField myField : myclass.fields) {
                                if(myField.name.equals(identifier)){
                                    containIdentifier = true;
                                    break;
                                }
                            }

                            if(!containIdentifier){
                                System.out.println(Constraint.CONSTRAINT_02+" in "+myclass.name);
                                return false;
                            }
                        }
                    }
                }

                if(!containsIdentifier){
                    System.out.println(Constraint.CONSTRAINT_01+" in "+myclass.name);
                    return false;
                }
            }
        }

        return true;
    }
}
