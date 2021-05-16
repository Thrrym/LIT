package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Reject extends Activity {
    private static final String type = "Activity";

    public Reject(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
