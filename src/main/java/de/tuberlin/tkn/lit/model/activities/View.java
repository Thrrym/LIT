package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.storage.IStorage;

public class View extends Activity {
    private static final String type = "View";

    public View(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, Activity activity, IStorage storage) {
        return null;
    }

    public View() {
    }

    public String getType() {
        return type;
    }
}
