package de.tuberlin.tkn.lit.model.objects;

import de.tuberlin.tkn.lit.model.ActivityPubObject;


public class Note extends ActivityPubObject {

    private static final String type = "Note";

    public String getType() {
        return type;
    }

}
