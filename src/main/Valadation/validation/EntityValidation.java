package main.Valadation.validation;


import main.Valadation.entity.OwnedAttribute;
import main.Valadation.entity.PackagedElement;
import main.Valadation.entity.XMI;
import main.Valadation.entity.tag.Entity;
import main.Valadation.parser.XMLParserUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class    EntityValidation {
    public static boolean entityCheck() throws IOException {

       // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Entity> it = xmi.getEntities().listIterator();
        HashSet<String> entitySet = new HashSet<String>();
        while (it.hasNext()) {
            Entity e = it.next();
            Iterator<OwnedAttribute> attributeIterator=e.getOwnedAttributes().listIterator();//C1. An entity has and only has one identity.
            while(attributeIterator.hasNext()) {
                OwnedAttribute attribute= attributeIterator.next();

                if (e.getIdentifier() != attribute.getType())   // /the identity of an entity should be one of the attributes of the entity itself
                    return false;

                else if (entitySet.contains(e.getIdentifier()) == false)
                    entitySet.add(e.getIdentifier());
                else
                    return false;

            }
        }

        return true;
    }

    //C2. An entity should has at least one domain behaviour.
    public static boolean entityCheck2() throws IOException {
        // String filePath = "src/main/resources/parser-test.xml";
        XMI xmi = XMLParserUtil.parserXML();

        Iterator<Entity> it = xmi.getEntities().listIterator();
        HashSet<String> entitySet = new HashSet<String>();
        while (it.hasNext()) {
            Entity e = it.next();

                Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();

                PackagedElement packagedElement =new PackagedElement();

                while (elementIterator.hasNext())
                {
                    PackagedElement packagedElement1 = elementIterator.next();
                    if(packagedElement1.getId()==e.getBaseClass())
                    {
                        packagedElement=packagedElement1;         //找到是entity的packagedElement
                        break;
                    }
                }


                //  PackagedElement packagedElement=elementIterator.next();
                //assert packagedElement!=null;



                    if( packagedElement.getOwnedOperations().isEmpty())
                        return false;
                }

            return true;
            }

}
