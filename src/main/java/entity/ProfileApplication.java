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
@JacksonXmlRootElement(localName = "profileApplication")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileApplication implements Serializable {
    private static final long serialVersionUID = -2997920678150203854L;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "eAnnotations")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<EAnnotations> eAnnotations;

    @JacksonXmlProperty(localName = "appliedProfile")
    private AppliedProfile appliedProfile;

    public ProfileApplication(String type, String id, List<EAnnotations> eAnnotations, AppliedProfile appliedProfile) {
        this.type = type;
        this.id = id;
        this.eAnnotations = eAnnotations;
        this.appliedProfile = appliedProfile;
    }

    public ProfileApplication() {
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

    public List<EAnnotations> geteAnnotations() {
        return eAnnotations;
    }

    public void seteAnnotations(List<EAnnotations> eAnnotations) {
        this.eAnnotations = eAnnotations;
    }

    public AppliedProfile getAppliedProfile() {
        return appliedProfile;
    }

    public void setAppliedProfile(AppliedProfile appliedProfile) {
        this.appliedProfile = appliedProfile;
    }

    @Override
    public String toString() {
        return "ProfileApplication{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", eAnnotations=" + eAnnotations +
                ", appliedProfile=" + appliedProfile +
                '}';
    }
}
