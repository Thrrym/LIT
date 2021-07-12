package de.tuberlin.tkn.lit.model.activitypub.social;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long objectID;
    private String objectType;
    private String actorname;
    private String link;


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

    public long getId() {
        return id;
    }

    //@OneToMany(targetEntity = ActivityPubObject.class)
    //@OneToOne(targetEntity = ActivityPubObject.class, cascade = CascadeType.ALL, optional = true, fetch = FetchType.LAZY)
    //@JoinColumn(name = "activity_pub_id")
    //@ElementCollection
    //private List<Long> outbox_ids;
    //@ElementCollection
    //private List<String> outbox_actors;
}
