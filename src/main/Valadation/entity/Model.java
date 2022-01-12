package main.Valadation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CZK
 * @since 1.0.0
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class Model implements Serializable {

    private static final long serialVersionUID = 8855603143525560907L;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "packageImport")
    private PackageImport packageImport;

    @JacksonXmlProperty(localName = "packagedElement")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PackagedElement> packagedElement;

    private ProfileApplication profileApplication;

    public Model(String id, String name, PackageImport packageImport, List<PackagedElement> packagedElement,
                 ProfileApplication profileApplication) {
        this.id = id;
        this.name = name;
        this.packageImport = packageImport;
        this.packagedElement = packagedElement;
        this.profileApplication = profileApplication;
    }

    public Model() {
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

    public PackageImport getPackageImport() {
        return packageImport;
    }

    public void setPackageImport(PackageImport packageImport) {
        this.packageImport = packageImport;
    }

    public List<PackagedElement> getPackagedElement() {
        //return packagedElement;
        return packagedElement != null ? packagedElement: new ArrayList<PackagedElement>();

    }

    public void setPackagedElement(List<PackagedElement> packagedElement) {
        this.packagedElement = packagedElement;
    }

    public ProfileApplication getProfileApplication() {
        return profileApplication;
    }

    public void setProfileApplication(ProfileApplication profileApplication) {
        this.profileApplication = profileApplication;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", packageImport=" + packageImport +
                ", packagedElement=" + packagedElement +
                ", profileApplication=" + profileApplication +
                '}';
    }
}
