package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class View extends Activity {
    private static final String type = "View";

    public View(Activity activity) {
        super(activity);
    }

    public View() {
    }

    public String getType() {
        return type;
    }
}
