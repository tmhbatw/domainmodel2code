package main.Valadation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "importedPackage")
public class ImportedPackage implements Serializable {

    private static final long serialVersionUID = 1533900509339825374L;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String href;

}
