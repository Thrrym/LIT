package de.tuberlin.tkn.lit.model.activitypub.social;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Followed extends ActivityPubObject {

    private String actorname;
    private long objectID;
    private String objectType;

    public void setActorname(String actorname) {
        this.actorname = actorname;
    }

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

    public String getActorname() {
        return actorname;
    }

}
