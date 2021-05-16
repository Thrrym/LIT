package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Dislike extends Activity {
    private static final String type = "Dislike";

    public Dislike(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
