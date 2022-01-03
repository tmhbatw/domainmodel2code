package main.java.entity.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import entity.OwnedAttribute;
import entity.OwnedOperation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CZK
 * @since 1.0.0
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class TagTemplate extends BaseTag implements Serializable {
    private static final long serialVersionUID = 7756989825310760243L;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true, localName = "base_Class")
    private String baseClass;

    @JacksonXmlProperty(isAttribute = true,localName = "base_Package")
    private String basePackage;


    @JacksonXmlProperty(localName = "ownedAttribute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OwnedAttribute> ownedAttributes;

    @JacksonXmlProperty(localName = "ownedOperation")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OwnedOperation> ownedOperations;


    public TagTemplate(String id, String baseClass, String basePackage) {
        this.id = id;
        this.baseClass = baseClass;
        this.basePackage = basePackage;
    }

    public TagTemplate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(String baseClass) {
        this.baseClass = baseClass;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setOwnedOperations(List<OwnedOperation> ownedOperations) {
        this.ownedOperations = ownedOperations;
    }

    public void setOwnedAttributes(List<OwnedAttribute> ownedAttributes) {
        this.ownedAttributes = ownedAttributes;
    }

    public List<OwnedOperation> getOwnedOperations() {
        return ownedOperations != null ? ownedOperations: new ArrayList<OwnedOperation>();
    }

    public List<OwnedAttribute> getOwnedAttributes() {
    //    return ownedAttributes;

        return ownedAttributes != null ? ownedAttributes : new ArrayList<OwnedAttribute>();
    }

    @Override
    public String toString() {
        return "TagTemplate{" +
                "id='" + id + '\'' +
                ", baseClass='" + baseClass + '\'' +
                ", basePackage='" + basePackage + '\'' +
                ", ownedAttributes=" + ownedAttributes +
                ", ownedOperations=" + ownedOperations +
                '}';
    }
}

