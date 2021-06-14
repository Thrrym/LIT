package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Update extends Activity {
    private static final String type = "Update";

    public Update() {
    }

    public Update(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage,int port) {
        LitObject updateObject;
        if (getObject().isObject()) {
            updateObject = storage.updateObject(actorName, getObject().getLitObject());
        } else {
            //TODO: Get object from link and persist it?
            updateObject = storage.updateObject(actorName, getObject().getLitObject());
        }
        setObject(new LinkOrObject(updateObject));
        return this;
    }

    public String getType() {
        return type;
    }
}
