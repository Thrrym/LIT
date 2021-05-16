package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Flag extends Activity {
    private static final String type = "Flag";

    public Flag(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
