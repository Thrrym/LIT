package de.tuberlin.tkn.lit.model.objects;

import de.tuberlin.tkn.lit.model.ActivityPubObject;

public class Event extends ActivityPubObject {
    private static final String type = "Event";

    public String getType() {
        return type;
    }
}
