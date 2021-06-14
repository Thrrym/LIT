package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Leave extends Activity {
    private static final String type = "Leave";

    public Leave() {
    }

    public Leave(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage) {
        return this;
    }

    public String getType() {
        return type;
    }
}
