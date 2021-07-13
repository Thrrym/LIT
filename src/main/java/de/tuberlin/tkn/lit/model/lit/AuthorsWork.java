package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;

@Entity
public class AuthorsWork extends ActivityPubObject {
    private long authorID;
    private long workID;
    private String workType;

    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    public long getWorkID() {
        return workID;
    }

    public void setWorkID(long workID) {
        this.workID = workID;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

}
