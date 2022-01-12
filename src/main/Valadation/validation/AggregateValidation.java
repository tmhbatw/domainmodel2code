package main.Valadation.validation;


import main.Valadation.entity.PackagedElement;
import main.Valadation.entity.XMI;
import main.Valadation.entity.tag.Aggregate;
import main.Valadation.entity.tag.Factory;
import main.Valadation.entity.tag.Repository;
import main.Valadation.parser.XMLParserUtil;

import java.io.IOException;
import java.util.Iterator;

public class AggregateValidation {


    // C16. An aggregate has one and only one aggregate root
    public static boolean aggregateCheck3() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Aggregate> it = xmi.getAggregates().listIterator();

        while (it.hasNext()) {
            int num = 0;
            Aggregate aggregate = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement = new PackagedElement();
            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId() == aggregate.getBaseClass())//该element是aggregate
                {
                    packagedElement = packagedElement1;
                    break;
                }
            }
            Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//聚合内部的成员

            while (elementIterator1.hasNext()) {
                PackagedElement packagedElement1 = elementIterator1.next();
                if (Support.isAggregateRoot(packagedElement1, xmi))
                num++;
            }
            if(num!=1) return false;
        }
        return true;


    }

    public static boolean aggregateCheck2() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Aggregate> it = xmi.getAggregates().listIterator();
        //  C17. Except the aggregate root, an aggregate can only contain aggregate parts
        while (it.hasNext()) {
            Aggregate aggregate = it.next();
            Iterator<PackagedElement> elementIterator = xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement = new PackagedElement();

            while (elementIterator.hasNext()) {
                PackagedElement packagedElement1 = elementIterator.next();
                if (packagedElement1.getId() == aggregate.getBaseClass())//该element是aggregate
                {
                    packagedElement = packagedElement1;
                    break;
                }
            }


            Iterator<PackagedElement> elementIterator1 = packagedElement.getPackagedElements().listIterator();//聚合内部的成员
            int num = 0;
            while (elementIterator1.hasNext()) {
                PackagedElement packagedElement1 = elementIterator1.next();
                if (Support.isAggregatePart(packagedElement1, xmi) || Support.isAggregateRoot(packagedElement1, xmi))
                    ;
                else
                    return false;
            }
        }

        return true;
    }



    //C18. The creation of an aggregate should be done by a factory.
    public static boolean aggregateCheck5() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<PackagedElement> it = xmi.getUmlModel().getPackagedElement().listIterator();

        while (it.hasNext()) {//遍历所有元素
            PackagedElement packagedElement = it.next();
            if (Support.isAggregate(packagedElement, xmi)) {//如果是聚合
                Iterator<Factory> factoryIterator = xmi.getFactories().listIterator();
                boolean temp =false;
                while(factoryIterator.hasNext())
                {
                    Factory factory =factoryIterator.next();
                    if(factory.getCreatingDomainObject()==packagedElement.getId());//必须有一个factory负责该聚合的创建
                    temp=true;
                }
                if(!temp) return  false;


            }


        }
        return true;
    }


    //C19. The accessing of an aggregate should be done by a repository.

        public static boolean aggregateCheck() throws IOException {
            XMI xmi = XMLParserUtil.parserXML();
            Iterator<PackagedElement> it = xmi.getUmlModel().getPackagedElement().listIterator();

            while (it.hasNext()) {//遍历所有元素
                PackagedElement packagedElement = it.next();
                if (Support.isAggregate(packagedElement, xmi)) {//如果是聚合
                    Iterator<Repository> repositoryIterator = xmi.getRepositories().listIterator();
                    boolean temp =false;
                    while(repositoryIterator.hasNext())
                    {
                        Repository repository =repositoryIterator.next();
                        if(repository.getAccessingDomainObject()==packagedElement.getId());//必须有一个资源库访问该聚合
                        temp=true;
                    }
                    if(!temp) return  false;


                }


            }
            return true;
        }









   /* //Association between any two aggregates is not allowed through object reference.   该条约束 貌似删除了
    public static boolean aggregateCheck4() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> it = xmi.getUmlModel().getPackagedElement().listIterator();

        HashSet<String> aggregateRootSet = new HashSet<String>();
        while (it.hasNext()) {//遍历所有元素
            PackagedElement packagedElement = it.next();
            if (Support.isAggregateRoot(packagedElement, xmi)) {//如果是聚合根
                aggregateRootSet.add(packagedElement.getId());//将所有聚合根的id存入set
                }
            }

        it=xmi.getUmlModel().getPackagedElement().listIterator();
        while (it.hasNext()) {//遍历所有元素
            PackagedElement packagedElement = it.next();
            if (Support.isAggregateRoot(packagedElement, xmi)) {//如果是聚合根
                Iterator<OwnedAttribute> attributeIterator = packagedElement.getOwnedAttributes().listIterator();

                while (attributeIterator.hasNext()) {   //遍历聚合根的所有属性
                    OwnedAttribute attribute = attributeIterator.next();
                    if(aggregateRootSet.contains(attribute.getType())) //属性中有另一聚合根
                        return  false;
                }
            }
        }
            return true;

    }*/




}
