package main.Valadation.entity.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/
public class Repository extends TagTemplate implements Serializable {

    private static final long serialVersionUID = 2580273475859023876L;


    @JacksonXmlProperty(isAttribute = true,localName = "accessingDomainObject")
    private String accessingDomainObject;


    public String getAccessingDomainObject() {
        return accessingDomainObject;
    }

    public void setAccessingDomainObject(String accessingDomainObject) {
        this.accessingDomainObject = accessingDomainObject;
    }
}
