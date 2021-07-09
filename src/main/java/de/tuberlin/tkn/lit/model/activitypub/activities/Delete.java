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
    public Activity handle(String actorId, IStorage storage,int port) {
        String objId = this.getObject().getLitObject().getId();


        ActivityPubObject toDel = storage.getObject(objId);
        if (toDel == null) {
        //TODO: Send to the server the object belongs to


           /* List<LinkOrObject> customTo = getTo();
            if (customTo != null) {
                customTo.add(new LinkOrObject(this.));
            } else {
                List<LinkOrObject> temp = new ArrayList<>();
                temp.add(new LinkOrObject(getObject().getLink()));
                setTo(temp);
            }*/


        } else
        {

//TODO: Delete it here
        Tombstone newObj = new Tombstone();
        newObj.setId(objId);
        storage.updateObject(actorId,newObj);

        }


        return this;
    }

    public String getType() {
        return type;
    }
}
