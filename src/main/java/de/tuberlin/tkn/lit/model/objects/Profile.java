package de.tuberlin.tkn.lit.model.objects;

import de.tuberlin.tkn.lit.model.ActivityPubObject;

public class Profile extends ActivityPubObject {
    private static final String type = "Profile";

    public String getType() {
        return type;
    }
}
