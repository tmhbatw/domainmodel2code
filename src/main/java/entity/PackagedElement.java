package main.java.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CZK
 * @since 1.0.0
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "packagedElement")
public class PackagedElement implements Serializable {
    private static final long serialVersionUID = 354782337204436621L;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "ownedAttribute")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OwnedAttribute> ownedAttributes;

    @JacksonXmlProperty(localName = "ownedOperation")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OwnedOperation> ownedOperations;

    @JacksonXmlProperty(localName = "packagedElement")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PackagedElement> packagedElements;

//    public PackagedElement(String type, String id, String name) {
//        this.type = type;
//        this.id = id;
//        this.name = name;
//    }

    public PackagedElement() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public List<OwnedAttribute> getOwnedAttributes() {
        return ownedAttributes != null ? ownedAttributes : new ArrayList<OwnedAttribute>();
    }

    public List<OwnedOperation> getOwnedOperations() {
        return ownedOperations != null ? ownedOperations: new ArrayList<OwnedOperation>();
    }

    public void setOwnedAttributes(List<OwnedAttribute> ownedAttributes) {
        this.ownedAttributes = ownedAttributes;
    }

    public void setOwnedOperations(List<OwnedOperation> ownedOperations) {
        this.ownedOperations = ownedOperations;
    }

    public List<PackagedElement> getPackagedElements() {

        return packagedElements != null ? packagedElements: new ArrayList<PackagedElement>();
    }

    public void setPackagedElements(List<PackagedElement> packagedElements) {
        this.packagedElements = packagedElements;
    }

    @Override
    public String toString() {
        return "PackagedElement{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownedAttributes=" + ownedAttributes +
                ", ownedOperations=" + ownedOperations +
                ", packagedElements=" + packagedElements +
                '}';
    }
}
