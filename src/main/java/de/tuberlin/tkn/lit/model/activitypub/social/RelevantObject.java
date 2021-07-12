package de.tuberlin.tkn.lit.model.activitypub.social;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RelevantObject extends ActivityPubObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long relevantObjectID;
    private String link;
    private long objectID;
    private String objectType;
    private String actorname;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public long getObjectID() {
        return objectID;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    public String getActorname() {
        return actorname;
    }

    public void setActorname(String actorname) {
        this.actorname = actorname;
    }

    public long getRelevantObjectID() {
        return relevantObjectID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    //@OneToMany(targetEntity = ActivityPubObject.class)
    //@OneToOne(targetEntity = ActivityPubObject.class, cascade = CascadeType.ALL, optional = true, fetch = FetchType.LAZY)
    //@JoinColumn(name = "activity_pub_id")
    //@ElementCollection
    //private List<Long> outbox_ids;
    //@ElementCollection
    //private List<String> outbox_actors;
}
