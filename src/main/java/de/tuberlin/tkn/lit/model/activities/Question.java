package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Question extends Activity {
    private static final String type = "Question";

    public Question() {}

    public Question(Activity activity) { super(activity); }

    @Override
    public Activity handle(String actorName, IStorage storage) {
        return this;
    }

    public String getType() { return type; }
}

