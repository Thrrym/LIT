package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.IntransitiveActivity;

public class Question extends IntransitiveActivity {
    private static final String type = "Question";

    public Question() {
    }

    public Question(Activity activity) {
        super(activity);
    }
    public String getType() {
        return type;
    }
}
