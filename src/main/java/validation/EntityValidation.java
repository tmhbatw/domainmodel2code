package main.java.validation;

import entity.OwnedAttribute;
import entity.XMI;
import entity.tag.Entity;
import parser.XMLParserUtil;

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
            Iterator<OwnedAttribute> attributeIterator=e.getOwnedAttributes().listIterator();
            while(attributeIterator.hasNext()) {
                OwnedAttribute attribute= attributeIterator.next();

                if (e.getIdentifier() != attribute.getType())
                    return false;

                else if (entitySet.contains(e.getIdentifier()) == false)
                    entitySet.add(e.getIdentifier());
                else
                    return false;

            }
        }

        return true;
    }
}
