package de.tuberlin.tkn.lit.model.objects;

import de.tuberlin.tkn.lit.model.ActivityPubObject;

public class Relationship extends ActivityPubObject {
    private static final String type = "Relationship";

    public String getType() {
        return type;
    }
}
