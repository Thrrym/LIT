package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Leave extends Activity {
    private static final String type = "Leave";

    public Leave() {
    }

    public Leave(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
