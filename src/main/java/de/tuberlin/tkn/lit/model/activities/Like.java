package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.LitObject;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Like extends Activity {
    private static final String type = "Like";

    public Like() {
    }

    public Like(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage) {
        if (getObject().isObject()) {
            storage.addToLikeCollection(actorName,getObject());
        } else {
            //TODO: Get object from link and persist it?
            storage.addToLikeCollection(actorName,getObject());
        }
        return this;
    }

    public String getType() {
        return type;
    }
}
