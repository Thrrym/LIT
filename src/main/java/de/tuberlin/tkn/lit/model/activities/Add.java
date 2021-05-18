package de.tuberlin.tkn.lit.model.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.model.Activity;

public class Add extends Activity {

    @JsonIgnore
    private static final String type = "Add";

    public Add() {
    }

    public Add(Activity activity) {
        super(activity);
    }
}
