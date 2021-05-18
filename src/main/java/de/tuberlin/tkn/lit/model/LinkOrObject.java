package de.tuberlin.tkn.lit.model;

import de.tuberlin.tkn.lit.model.objects.Link;

public class LinkOrObject {
    private String link;
    private LitObject litObject;

    public LinkOrObject(String link) {
        this.link = link;
        litObject = null;
    }

    public LinkOrObject(LitObject object) {
        this.litObject = object;
        link = null;
    }

    public boolean isObject() {
        return litObject != null ? true : false;
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

    public void setLitObject(LitObject object) {
        this.litObject = object;
    }
}
