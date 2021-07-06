package de.tuberlin.tkn.lit.model.activitypub.objects;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;


public class Note extends ActivityPubObject {

    private static final String type = "Note";

    public String getType() {
        return type;
    }

}
