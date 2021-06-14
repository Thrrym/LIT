package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.storage.IStorage;

public class View extends Activity {
    private static final String type = "View";

    public View(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName,  IStorage storage,int port) {
        return this;
    }

    public View() {
    }

    public String getType() {
        return type;
    }
}
