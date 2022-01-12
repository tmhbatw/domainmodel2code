package main.Valadation.entity.tag;

import java.io.Serializable;

/**
 * @author CZK
 * @since 1.0.0
 **/
public class Factory extends TagTemplate implements Serializable {

    private static final long serialVersionUID = -2346681490492811041L;
    private String creatingDomainObject;


    public String getCreatingDomainObject() {
        return creatingDomainObject;
    }

    public void setCreatingDomainObject(String creatingDomainObject) {
        this.creatingDomainObject = creatingDomainObject;
    }

    @Override
    public String toString() {
        return "Factory{" +
                "creatingDomainObject='" + creatingDomainObject + '\'' +
                '}';
    }
}
