package main.codeValidation;

import main.Code2Model;
import main.ModelCompared;
import main.metainfo.MyAnnotation;
import main.metainfo.MyClass;
import main.metainfo.MyField;
import main.metainfo.MyPackage;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CodeValidate {
    public boolean validate(String path) throws ClassNotFoundException {
        Code2Model code =new Code2Model(path);

        Map<String,MyClass> classInfo=new ModelCompared().changeClassNameFormat(code.getClassInfo());
        Map<String, MyPackage> packageInfo=code.getPackageInfo();

        if(!new packageValidation().packageValidate(packageInfo))
            return false;


        Map<String,String> domainObjectType = new HashMap<>();
        Map<String,String> aggregateType = new HashMap<>();
        Map<String,String> aggregateName = new HashMap<>();
        for(String className : classInfo.keySet()){
            MyClass myClass = classInfo.get(className);
            if(myClass.annotations.size()==0){
                System.out.println("no steretype found err in class "+className);
                return false;
            }

            for(MyAnnotation cur:myClass.annotations){
                switch (cur.name){
                    case "Entity":
                    case "ValueObject":
                    case "DomainEvent":
                    case "DomainService":
                    case "Repository":
                    case "Factory":
                        if(domainObjectType.containsKey(className)){
                            System.out.println("too many domain object type found in class "+className);
                            return false;
                        }
                        domainObjectType.put(className,cur.name);
                        break;
                    case "AggregateRoot":
                        if(aggregateType.containsKey(className)){
                            System.out.println("too many aggregate type found in class "+className);
                            return false;
                        }
                        aggregateType.put(className,cur.name);
                        aggregateName.put(className,className);
                        break;
                    case "AggregatePart":
                        if(aggregateType.containsKey(className)){
                            System.out.println("too many aggregate type found in class "+className);
                            return false;
                        }
                        String curAggregateName = Util.getParameterFromList(cur.parameters,"aggregateRootType");
                        aggregateName.put(className,curAggregateName);
                        aggregateType.put(className,cur.name);
                }
            }
        }

        for(String className:aggregateType.keySet()){
            if(!domainObjectType.containsKey(className)){
                System.out.println("no domain object type found in class "+className);
                return false;
            }
            if(domainObjectType.get(className).equals("DomainService")){
                System.out.println(Constraint.CONSTRAINT_11 +" in "+className);
                return false;
            }
            if(domainObjectType.get(className).equals("Factory")){
                System.out.println(Constraint.CONSTRAINT_14+" in "+className);
                return false;
            }
            if(domainObjectType.get(className).equals("Repository")){
                System.out.println(Constraint.CONSTRAINT_16+ " in "+className);
                return false;
            }
            if(aggregateType.get(className).equals("AggregateRoot")){
                if(!domainObjectType.get(className).equals("Entity")){
                    System.out.println(Constraint.CONSTRAINT_17+" in "+className);
                    return false;
                }
            }
            if(aggregateType.get(className).equals("AggregatePart")){
                if(!domainObjectType.get(className).equals("Entity")
                        &&!domainObjectType.get(className).equals("ValueObject")){
                    System.out.println(Constraint.CONSTRAINT_18+" in "+className);
                    return false;
                }
            }
        }

        for(String className : classInfo.keySet()){
            DomainObjectValidate validator = ValidatorFactory.getValidator(domainObjectType.get(className));
            if(validator==null){
                System.out.println("domain object type not find in class "+className);
                return false;
            }
            if(!validator.validate(classInfo.get(className),classInfo))
                return false;
        }

        if(!new AggregateValidate().validate(packageInfo,aggregateType)){
            return false;
        }


        for(String className : aggregateType.keySet()){
            if(aggregateType.get(className).equals("AggregateRoot")){
                if(!FactoryValidate.set.contains(className)){
                    System.out.println(Constraint.CONSTRAINT_23+" in "+className+", 建议进行调整！");
                }
                if(!RepositoryValidate.set.contains(className)){
                    System.out.println(Constraint.CONSTRAINT_24+" in "+className+", 建议进行调整！");
                }
            }
        }


        Map<String,String> class2PackageMap = new HashMap<>();
        for(String packageName : packageInfo.keySet()) {
            MyPackage myPackage = packageInfo.get(packageName);
            for(String className :myPackage.className){
                class2PackageMap.put(className,packageName);
            }
        }

        for(String className : classInfo.keySet()){
            MyClass myClass = classInfo.get(className);

            for(MyField f:myClass.fields){
                if(classInfo.containsKey(f.type)){
                    if(!class2PackageMap.get(f.type).equals(class2PackageMap.get(className))){
                        System.out.println(Constraint.CONSTRAINT_25+" in "+className);
                        return false;
                    }
                }
            }
        }

        for(String className :classInfo.keySet()){
            MyClass myClass = classInfo.get(className);

            for(String depend:myClass.dependency){
                if(depend.equals(className)){
                    continue;
                }

                if(aggregateType.containsKey(depend)&&aggregateType.get(depend).equals("AggregatePart")){
                    String aggregateRootName = aggregateName.get(depend);
                    if(!aggregateType.containsKey(className)||
                        !aggregateName.get(className).equals(aggregateRootName)){
                        System.out.println(Constraint.CONSTRAINT_20+ " in "+depend);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(new CodeValidate().validate("main.gen.model"));
    }
}
