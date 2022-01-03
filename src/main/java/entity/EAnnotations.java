package main.java.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.List;

/**
 * @author CZK
 * @since 1.0.0
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "eAnnotations")
public class EAnnotations implements Serializable {
    private static final long serialVersionUID = 5000028453133218570L;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String source;

    @JacksonXmlProperty(localName = "details")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Details> details;

    @JacksonXmlProperty(localName = "references")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<References> references;

    public EAnnotations(String type, String id, String source, List<Details> details) {
        this.type = type;
        this.id = id;
        this.source = source;
        this.details = details;
    }

    public EAnnotations(String type, String id, String source, List<Details> details, List<References> references) {
        this.type = type;
        this.id = id;
        this.source = source;
        this.details = details;
        this.references = references;
    }

    public EAnnotations() {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public List<References> getReferences() {
        return references;
    }

    public void setReferences(List<References> references) {
        this.references = references;
    }

    @Override
    public String toString() {
        return "EAnnotations{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", details=" + details +
                ", references=" + references +
                '}';
    }
}
