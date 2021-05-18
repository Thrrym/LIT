package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Move extends Activity {
    private static final String type = "Move";

    public Move() {
    }

    public Move(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
