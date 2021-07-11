package de.tuberlin.tkn.lit.model.activitypub.activities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.storage.IStorage;

public class Accept extends Activity {

    @JsonIgnore
    private static final String type = "Accept";

    public Accept() {
    }

    public Accept(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage, int port) {
        Activity update = new Update(this);
        Activity offer = storage.getActivity(getObject().getId());
        update.setType("Update");
        update.setObject(offer.getObject());
        return update;
    }
}
