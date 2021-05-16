package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Read extends Activity {
    private static final String type = "Read";

    public Read(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
