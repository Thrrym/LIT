package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

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
        if (getActor().isObject()){
            getActor().getLitObject().setId(storage.getActor(actorName).getId());
        }

        ActivityPubObject createdObject;
        if (getObject().isObject()) {
            createdObject = storage.createObject(actorName, getObject().getLitObject().getType(), getObject().getLitObject());
        } else {
            //TODO: Get object from link and persist it?
            createdObject = storage.createObject(actorName, getObject().getLitObject().getType(), getObject().getLitObject());
        }

        setObject(new LinkOrObject(createdObject));

        // Inform followers
        //            if (storage.getFollowersCollection(getActor().getLitObject().getName()).getOrderedItems() != null)
        if (storage.getFollowersCollection(UriUtilities.getActor(getActor().getId())).getOrderedItems() != null)
            setTo(storage.getFollowersCollection(UriUtilities.getActor(getActor().getId())).getOrderedItems());

        return this;
    }
}
