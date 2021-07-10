package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

public class Update extends Activity {
    private static final String type = "Update";

    public Update() {
    }

    public Update(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        ActivityPubObject updateObject;
        if (storage.getObject(getObject().getId()) != null) {
            if (getObject().isObject()) {
                updateObject = storage.updateObject(actorName, getObject().getLitObject());
            }
            return this;
        }
        return null;
    }
}