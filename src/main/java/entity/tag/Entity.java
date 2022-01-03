package main.java.entity.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/
public class Entity extends TagTemplate implements Serializable {

    private static final long serialVersionUID = 550261141909134026L;

    @JacksonXmlProperty(isAttribute = true,localName = "Identity")
    private String identifier;

    public Entity() {

    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


}
