package main.Valadation.validation;


import main.Valadation.entity.PackagedElement;
import main.Valadation.entity.XMI;
import main.Valadation.entity.tag.Aggregate;
import main.Valadation.entity.tag.AggregatePart;
import main.Valadation.entity.tag.AggregateRoot;

import java.util.Iterator;

public class Support {

    public static boolean isAggregatePart(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<AggregatePart> it=xmi.getAggregateParts().listIterator();

        while (it.hasNext())
        {
            AggregatePart aggregatePart=it.next();
            if(aggregatePart.getBaseClass()==packagedElement.getId())
                return true;
        }

        return false;
    }


    public static boolean isAggregateRoot(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<AggregateRoot> it=xmi.getAggregateRoots().listIterator();

        while (it.hasNext())
        {
            AggregateRoot aggregateRoot=it.next();
            if(aggregateRoot.getBaseClass()==packagedElement.getId())
                return true;
        }

        return false;
    }

    public static boolean isAggregate(PackagedElement packagedElement, XMI xmi)
    {
        Iterator<Aggregate> it=xmi.getAggregates().listIterator();

        while (it.hasNext())
        {
            Aggregate aggregate=it.next();
            if(aggregate.getBaseClass()==packagedElement.getId())
                return true;
        }

        return false;
    }
}
