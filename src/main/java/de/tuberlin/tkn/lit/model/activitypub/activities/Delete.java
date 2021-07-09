package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.objects.Tombstone;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

import java.util.ArrayList;
import java.util.List;

public class Delete extends Activity {
    private static final String type = "Delete";

    public Delete() {
    }

    public Delete(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorId, IStorage storage, int port) {
        String objId = this.getObject().getId();

        ActivityPubObject toDel = storage.getObject(objId);
        if (toDel == null) {
            return null;
        }
        Tombstone newObj = new Tombstone();
        newObj.setId(objId);
        storage.updateObject(actorId, newObj);
        return this;
    }

    public String getType() {
        return type;
    }
}
