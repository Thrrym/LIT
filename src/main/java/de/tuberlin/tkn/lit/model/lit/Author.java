package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Entity
public class Author extends ActivityPubObject implements Serializable {

    private String orcid;
    private int publicationCount;
    private String firstName;
    private String lastName;
    @ElementCollection
    private List<String> likedBy;

    public Author() {
    }

    public Author(int publicationCount, String firstName, String lastName, String orcid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.publicationCount = publicationCount;
        this.orcid = orcid;
    }

    public int getPublicationCount() {
        return publicationCount;
    }

    public void setPublicationCount(int publicationCount) {
        this.publicationCount = publicationCount;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
