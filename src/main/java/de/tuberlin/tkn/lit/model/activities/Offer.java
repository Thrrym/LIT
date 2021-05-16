package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Offer extends Activity {
    private static final String type = "Offer";

    public Offer(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
