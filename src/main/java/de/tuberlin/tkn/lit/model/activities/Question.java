package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;

public class Question extends Activity {
    private static final String type = "Question";

    public Question() {}

    public Question(Activity activity) { super(activity); }

    public String getType() { return type; }
}

