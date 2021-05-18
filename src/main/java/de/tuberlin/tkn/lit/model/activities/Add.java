package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Add extends Activity {
    private static final String type = "Add";

    public Add() {
    }

    public Add(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
