package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Accept extends Activity {
    private static final String type = "Accept";


    public Accept(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
