package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Update extends Activity {
    private static final String type = "Update";

    public Update(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
