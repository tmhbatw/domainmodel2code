package main.Valadation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/

@JacksonXmlRootElement(localName = "ownedOperation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnedOperation implements Serializable {

    private static final long serialVersionUID = 8403537210358514162L;
    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    public OwnedOperation(String type, String id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public OwnedOperation() {
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
        return "OwnedOperation{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
