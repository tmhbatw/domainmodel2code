package main.Valadation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/

@JacksonXmlRootElement(localName = "ownedAttribute")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnedAttribute implements Serializable {


    private static final long serialVersionUID = -1516679362663214866L;
    @JacksonXmlProperty(isAttribute = true)
    private String type;     //无type时默认为xmi：type

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    public OwnedAttribute(String type, String id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public OwnedAttribute() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OwnedAttribute{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
