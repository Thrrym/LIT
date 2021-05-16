package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Join extends Activity {
    private static final String type = "Join";

    public Join(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
