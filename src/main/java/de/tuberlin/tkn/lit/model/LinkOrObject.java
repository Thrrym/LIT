package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkOrObject {
    private String link;
    private LitObject litObject;

    public LinkOrObject(String link) {
        this.link = link;
        litObject = null;
    }

    public LinkOrObject(LitObject litObject) {
        this.litObject = litObject;
        link = null;
    }

    public boolean isObject() {
        return litObject != null;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LitObject getLitObject() {
        return litObject;
    }

    public void setLitObject(LitObject litObject) {
        this.litObject = litObject;
    }
}
