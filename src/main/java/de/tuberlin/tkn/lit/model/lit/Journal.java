package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;

@Entity
public class Journal extends ActivityPubObject {
    private String longName;
    private String shortName;
    private String issn;
    private String eissn;

    public Journal() {

    }

}
