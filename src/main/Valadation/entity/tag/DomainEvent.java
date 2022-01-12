package main.Valadation.entity.tag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/
public class DomainEvent extends TagTemplate implements Serializable {

    private static final long serialVersionUID = -8281616205562160675L;

    @JacksonXmlProperty(isAttribute = true,localName = "Identity")
    private String identity;

    public DomainEvent() {

    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

}
