package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.storage.IStorage;

public class Follow extends Activity {
    private static final String type = "Follow";

    public Follow() {
    }

    public Follow(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        try {
            storage.addToFollowers(actorName, getObject());
        } catch (Exception ex) {
            return null;
        }
        return this;
    }

    public String getType() {
        return type;
    }
}
