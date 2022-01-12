package main.Valadation.validation;


import main.Valadation.entity.OwnedAttribute;
import main.Valadation.entity.PackagedElement;
import main.Valadation.entity.XMI;
import main.Valadation.entity.tag.ValueObject;
import main.Valadation.parser.XMLParserUtil;

import java.io.IOException;
import java.util.Iterator;

public class ValueObjectValidation {          // C3. A value object does not have an identity
    public static boolean valueObjectCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<ValueObject> it = xmi.getValueObjects().listIterator();

        while (it.hasNext())

        {
            ValueObject valueObject=it.next();

            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();

            PackagedElement packagedElement =new PackagedElement();

            while (elementIterator.hasNext())
            {
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId()==valueObject.getBaseClass())
                {
                    packagedElement=packagedElement1;
                    break;
                }
            }


          //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;

            Iterator<OwnedAttribute> ownedAttributeIterator=packagedElement.getOwnedAttributes().listIterator();

            while (ownedAttributeIterator.hasNext())
            {
                OwnedAttribute ownedAttribute= ownedAttributeIterator.next();
                if(ownedAttribute.getName().indexOf("identity")!=-1||ownedAttribute.getName().indexOf("Identity")!=-1)
                    return false;
            }
        }


        return true;
    }
}
