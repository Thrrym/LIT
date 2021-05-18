package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Undo extends Activity {
    private static final String type = "Undo";

    public Undo() {
    }

    public Undo(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
