package de.tuberlin.tkn.lit.model.activitypub.social;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String actorname;
    private long objectID;
    private String objectType;

    public long getObjectID() {
        return objectID;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setActorname(String actorname) {
        this.actorname = actorname;
    }

    public String getActorname() {
        return actorname;
    }
}