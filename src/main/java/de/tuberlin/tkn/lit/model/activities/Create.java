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
    public Activity handle(String actorName,Activity activity, IStorage storage){
        LitObject createdObject;
        if (activity.getObject().isObject()) {
            createdObject = storage.createObject(actorName, activity.getObject().getLitObject().getType(), activity.getObject().getLitObject());
        } else {
            //TODO: Get object from link and persist it?
            createdObject = storage.createObject(actorName, activity.getObject().getLitObject().getType(), activity.getObject().getLitObject());
        }

        activity.setObject(new LinkOrObject(createdObject));

        return activity;
    }
}
