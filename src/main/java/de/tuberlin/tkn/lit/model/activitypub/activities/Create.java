package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.IStorage;

import java.util.ArrayList;
import java.util.List;

public class Create extends Activity {

    public Create() {
    }

    public Create(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        ActivityPubObject createdObject;
        if (getObject().isObject()) {
            createdObject = storage.createObject(actorName, getObject().getLitObject().getType(), getObject().getLitObject());
        } else {
            //TODO: Get object from link and persist it?
            createdObject = storage.createObject(actorName, getObject().getLitObject().getType(), getObject().getLitObject());
        }

        setObject(new LinkOrObject(createdObject));

        // Inform followers
        setTo(storage.getFollowersCollection(getActor().getId()).getOrderedItems());

        return this;
    }
}
