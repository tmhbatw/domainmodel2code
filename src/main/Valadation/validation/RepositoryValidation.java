package main.Valadation.validation;

import main.Valadation.entity.PackagedElement;
import main.Valadation.entity.XMI;
import main.Valadation.entity.tag.Repository;
import main.Valadation.parser.XMLParserUtil;

import java.io.IOException;
import java.util.Iterator;

public class RepositoryValidation {
    public static boolean repositoryCheck() throws IOException {//C11. A repository has no attributes
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
