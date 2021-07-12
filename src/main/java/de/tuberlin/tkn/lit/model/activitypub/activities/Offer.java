package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.storage.IStorage;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OfferActivity")
public class Offer extends Activity{
    private static final String type = "Offer";

    public Offer() {}

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        return this;
    }


}
