package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Create extends Activity {

    public Create() {
    }

    public Create(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        LitObject createdObject;
        if (getObject().isObject()) {
            createdObject = storage.createObject(actorName, getObject().getLitObject().getType(), getObject().getLitObject());
        } else {
            //TODO: Get object from link and persist it?
            createdObject = storage.createObject(actorName, getObject().getLitObject().getType(), getObject().getLitObject());
        }

        setObject(new LinkOrObject(createdObject));

        return this;
    }
}
