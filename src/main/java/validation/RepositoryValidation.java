package main.java.validation;

import entity.OwnedAttribute;
import entity.PackagedElement;
import entity.XMI;
import entity.tag.DomainService;
import entity.tag.Repository;
import entity.tag.ValueObject;
import parser.XMLParserUtil;

import java.io.IOException;
import java.util.Iterator;

public class RepositoryValidation {
    public static boolean repositoryCheck() throws IOException {
        XMI xmi = XMLParserUtil.parserXML();
        Iterator<Repository> it = xmi.getRepositories().listIterator();

        while (it.hasNext())
        {
            Repository repository=it.next();
            Iterator<PackagedElement> elementIterator=xmi.getUmlModel().getPackagedElement().listIterator();
            PackagedElement packagedElement =new PackagedElement();
            while (elementIterator.hasNext())
            {
                PackagedElement packagedElement1 = elementIterator.next();
                if(packagedElement1.getId()==repository.getBaseClass())
                {
                    packagedElement=packagedElement1;
                    break;
                }
            }
            //  PackagedElement packagedElement=elementIterator.next();
            //assert packagedElement!=null;
            if(!packagedElement.getOwnedAttributes().isEmpty())
                return false;
        }
        return true;
    }
}
