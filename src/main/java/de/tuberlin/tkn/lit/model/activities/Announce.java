package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Announce extends Activity {
    private static final String type = "Announce";

    public Announce() {
    }

    public Announce(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
