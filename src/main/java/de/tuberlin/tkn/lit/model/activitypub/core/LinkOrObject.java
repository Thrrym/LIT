package de.tuberlin.tkn.lit.model.activitypub.core;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkOrObject {
    private String link;
    private ActivityPubObject activityPubObject;

    public LinkOrObject(String link) {
        this.link = link;
        activityPubObject = null;
    }

    public LinkOrObject(ActivityPubObject activityPubObject) {
        this.activityPubObject = activityPubObject;
        link = null;
    }

    public boolean isObject() {
        return activityPubObject != null;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ActivityPubObject getLitObject() {
        return activityPubObject;
    }

    public void setLitObject(ActivityPubObject activityPubObject) {
        this.activityPubObject = activityPubObject;
    }
    
    public String getId() {
    	return isObject() ? activityPubObject.getId() : link;
    }
}
