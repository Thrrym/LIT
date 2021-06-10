package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Flag extends Activity {
    private static final String type = "Flag";

    public Flag() {
    }

    public Flag(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, Activity activity, IStorage storage) {
        return null;
    }

    public String getType() {
        return type;
    }
}
