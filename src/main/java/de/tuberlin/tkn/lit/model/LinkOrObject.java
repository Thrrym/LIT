package de.tuberlin.tkn.lit.model;

import de.tuberlin.tkn.lit.model.objects.Link;

public class LinkOrObject {
    private String link;
    private LitObject object;

    public LinkOrObject(String link) {
        this.link = link;
        object = null;
    }

    public LinkOrObject(LitObject object) {
        this.object = object;
        link = null;
    }

    public boolean isObject() {
        return object != null ? true : false;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LitObject getObject() {
        return object;
    }

    public void setObject(LitObject object) {
        this.object = object;
    }
}
