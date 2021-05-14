package de.tuberlin.tkn.lit.model.objects;

import de.tuberlin.tkn.lit.model.ActivityPubObject;

public class Place extends ActivityPubObject {
    private static final String type = "Place";

    public String getType() {
        return type;
    }
}
