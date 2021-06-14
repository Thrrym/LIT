package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Move extends Activity {
    private static final String type = "Move";

    public Move() {
    }

    public Move(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage,int port) {
        return this;
    }

    public String getType() {
        return type;
    }
}
