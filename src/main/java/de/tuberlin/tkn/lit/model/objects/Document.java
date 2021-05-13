package de.tuberlin.tkn.lit.model.objects;

import de.tuberlin.tkn.lit.model.ActivityPubObject;

public class Document extends ActivityPubObject {
    private static final String type = "Document";

    public String getType() {
        return type;
    }
}
