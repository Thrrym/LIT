package de.tuberlin.tkn.lit.model.objects;

import de.tuberlin.tkn.lit.model.ActivityPubObject;

public class Article extends ActivityPubObject {
    private static final String type = "Article";

    public String getType() {
        return type;
    }
}
