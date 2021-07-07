package de.tuberlin.tkn.lit.model.lit;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

import javax.persistence.Entity;

@Entity
public class Proceeding extends ActivityPubObject {
    private String isbn;
    private String issn;
    private String eissn;
    private int volume;
}
