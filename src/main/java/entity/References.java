package main.java.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/

@JacksonXmlRootElement(localName = "references")
@JsonIgnoreProperties(ignoreUnknown = true)

public class References implements Serializable {

    private static final long serialVersionUID = 4170127373741059602L;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String href;

    public References(String type, String href) {
        this.type = type;
        this.href = href;
    }

    public References() {
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
        return "References{" +
                "type='" + type + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
