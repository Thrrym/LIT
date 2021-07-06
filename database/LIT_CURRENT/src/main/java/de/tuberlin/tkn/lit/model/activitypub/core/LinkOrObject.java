package de.tuberlin.tkn.lit.model.activitypub.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class LinkOrObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String link;
    @OneToOne(targetEntity = ActivityPubCollection.class)
    private ActivityPubObject activityPubObject;

    public LinkOrObject(String link) {
        this.link = link;
        activityPubObject = null;
    }

    public LinkOrObject(ActivityPubObject activityPubObject) {
        this.activityPubObject = activityPubObject;
        link = null;
    }

    public LinkOrObject() {

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
    	return isObject() ? activityPubObject.getAp_id() : link;
    }

    public void setId(long id) { this.id = id; }
}
