package de.tuberlin.tkn.lit.model.activitypub.objects;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

public class Tombstone extends ActivityPubObject {
    private static final String type = "Tombstone";

    public String getType() {
        return type;
    }
}
