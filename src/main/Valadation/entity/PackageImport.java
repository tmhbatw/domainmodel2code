package main.Valadation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/
@JacksonXmlRootElement(localName = "packageImport")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageImport implements Serializable {

    private static final long serialVersionUID = -3586397560854891080L;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "importedPackage")
    private ImportedPackage importedPackage;

    public PackageImport(String type, String id, ImportedPackage importedPackage) {
        this.type = type;
        this.id = id;
        this.importedPackage = importedPackage;
    }

    public PackageImport() {
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

    public ImportedPackage getImportedPackage() {
        return importedPackage;
    }

    public void setImportedPackage(ImportedPackage importedPackage) {
        this.importedPackage = importedPackage;
    }

    @Override
    public String toString() {
        return "PackageImport{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", importedPackage=" + importedPackage +
                '}';
    }
}
