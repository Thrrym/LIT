package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.storage.IStorage;

public class Undo extends Activity {
    private static final String type = "Undo";

    public Undo() {
    }

    public Undo(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName , IStorage storage,int port) {
        return this;
    }

    public String getType() {
        return type;
    }
}
