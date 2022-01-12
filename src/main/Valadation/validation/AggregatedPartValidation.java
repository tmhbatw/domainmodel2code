package main.Valadation.validation;


import main.Valadation.entity.OwnedAttribute;
import main.Valadation.entity.PackagedElement;
import main.Valadation.entity.XMI;
import main.Valadation.parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class AggregatedPartValidation {
    public static boolean aggregatePartCheck() throws IOException {// C15. The reference of an aggregate part cannot be held by the outside objects
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<PackagedElement> itAggregate = xmi.getUmlModel().getPackagedElement().listIterator();

        while (itAggregate.hasNext()) {//遍历所有element，找到是聚合的

            HashSet<String> aggregatePartSet = new HashSet<String>();//把该聚合的所有aggregatePart的id到set里

            PackagedElement aggregate = itAggregate.next();

            if (Support.isAggregate(aggregate, xmi)) {

                Iterator<PackagedElement> itAggregatePart = aggregate.getPackagedElements().listIterator();
                while (itAggregatePart.hasNext()) {
                    PackagedElement aggregatePart = itAggregatePart.next();
                    ;//把该聚合的所有aggregatePart的id到set里
                    if (Support.isAggregatePart(aggregatePart, xmi)) {
                        aggregatePartSet.add(aggregatePart.getId());

                    }
                }

                Iterator<PackagedElement> itOtherAggregate = xmi.getUmlModel().getPackagedElement().listIterator();
                while (itOtherAggregate.hasNext()) {//遍历其他element，找到是聚合的

                    PackagedElement otherAggregate = itOtherAggregate.next();
                    if (Support.isAggregate(otherAggregate, xmi) && (otherAggregate.getId() != aggregate.getId()))//其他聚合
                    {
                        Iterator<PackagedElement> itOtherAggregatePart = otherAggregate.getPackagedElements().listIterator();//遍历其他聚合内部的聚合部分
                        while (itOtherAggregatePart.hasNext()) {
                            PackagedElement otherAggregatePart = itOtherAggregatePart.next();
                            if (Support.isAggregatePart(otherAggregatePart, xmi)) {
                                Iterator<OwnedAttribute> attributeIterator = otherAggregatePart.getOwnedAttributes().listIterator();

                                while (attributeIterator.hasNext()) {
                                    OwnedAttribute attribute = attributeIterator.next();          //属性中引用了其他的聚合部分
                                    if (aggregatePartSet.contains(attribute.getType()))
                                        return false;
                                }
                            }
                        }
                    }

                }


            }

        }

        return true;
    }
}

