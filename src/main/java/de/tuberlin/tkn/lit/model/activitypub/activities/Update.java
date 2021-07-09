package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.storage.IStorage;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UpdateActivity")
public class Update extends Activity {
    private static final String type = "Update";

    public Update() {
    }

    public Update(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage,int port) {
        ActivityPubObject updateObject;
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
