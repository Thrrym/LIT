package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Create extends Activity {
    private static final String type = "Create";

    public Create() {}

    public Create(Activity activity) {
        super(activity);
    }

    @Override
    public String getType() {
        return type;
    }
}
