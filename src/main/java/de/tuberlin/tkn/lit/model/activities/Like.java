package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Like extends Activity {
    private static final String type = "Like";

    public Like(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
