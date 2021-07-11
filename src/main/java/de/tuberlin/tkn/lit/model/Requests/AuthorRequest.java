package de.tuberlin.tkn.lit.model.Requests;

import javax.validation.constraints.NotNull;

public class AuthorRequest {

    @NotNull
    private String orcid;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    public AuthorRequest() {
    }

    public AuthorRequest(String firstName, String lastName, String orcid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.orcid = orcid;
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
