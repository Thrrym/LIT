package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;

@Entity
public class Series extends ActivityPubObject {
    private String shortName;
    private String longName;
}
