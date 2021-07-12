package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Author extends ActivityPubObject {

    @NotNull
    private String orcid;
    private int publicationCount;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    public Author() {
    }

    public Author(String firstName, String lastName, String orcid) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Author)) {
            return false;
        }

        Author author = (Author) o;

        return orcid == author.getOrcid();
    }
}