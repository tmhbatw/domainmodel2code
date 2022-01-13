package main.codeValidation;

import main.metainfo.MyAnnotation;
import main.metainfo.MyAnnotationParameter;
import main.metainfo.MyClass;
import main.metainfo.MyField;

import java.util.Map;

public class DomainEventValidate extends DomainObjectValidate{
    @Override
    public boolean validate(MyClass myclass, Map<String,MyClass> map) {
        String className = myclass.name;
        for(MyAnnotation cur:myclass.annotations){
            if(cur.name.equals("DomainEvent")){
                boolean containIdentifier = false;

                for(MyAnnotationParameter para:cur.parameters){
                    if(para.name.equals("identifier")){
                        containIdentifier = true;

                        String[] identifier = para.value.split(" ");
                        for(String i:identifier){
                            boolean validIdentifier = false;

                            for(MyField myField:myclass.fields){
                                if(myField.name.equals(i)){
                                    validIdentifier = true;
                                    break;
                                }
                            }

                            if(!validIdentifier){
                                System.out.println(Constraint.CONSTRAINT_06+" in "+className);
                                return false;
                            }
                        }
                    }

                    if(para.name.equals("timeStamp")){
                        boolean containTimeStamp = false;

                        String timeStamp = para.value;
                        for(MyField myField:myclass.fields){
                            if(myField.name.equals(timeStamp)){
                                containTimeStamp = true;
                                break;
                            }
                        }

                        if(!containTimeStamp){
                            System.out.println(Constraint.CONSTRAINT_07+" in "+className);
                            return false;
                        }
                    }

                    if(para.name.equals("publisher")){
                        if(!map.containsKey(para.value)){
                            System.out.println(Constraint.CONSTRAINT_08+" in "+className);
                            return false;
                        }
                    }
                    if(para.name.equals("subscriber")){
                        if(!map.containsKey(para.value)){
                            System.out.println(Constraint.CONSTRAINT_08+" in "+className);
                            return false;
                        }
                    }
                }

                if(!containIdentifier) {
                    System.out.println(Constraint.CONSTRAINT_05+" in "+className);
                    return false;
                }
            }
        }

        for(MyField myField:myclass.fields){
            if(myField.modifier.equals("private")){
                System.out.println(Constraint.CONSTRAINT_09+" in "+className);
                return false;
            }
        }
        return true;
    }
}
