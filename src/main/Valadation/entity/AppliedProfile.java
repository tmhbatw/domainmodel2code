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
@JacksonXmlRootElement(localName = "appliedProfile")
public class AppliedProfile implements Serializable {
    private static final long serialVersionUID = 8953238787772783836L;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String href;

    public AppliedProfile(String type, String href) {
        this.type = type;
        this.href = href;
    }

    public AppliedProfile() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "AppliedProfile{" +
                "type='" + type + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
