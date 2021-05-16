package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Remove extends Activity {
    private static final String type = "Remove";

    public Remove(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
