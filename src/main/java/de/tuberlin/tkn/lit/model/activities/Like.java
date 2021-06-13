package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.*;
import de.tuberlin.tkn.lit.storage.IStorage;

import java.util.UUID;

public class Like extends Activity {
    private static final String type = "Like";

    public Like() {
    }

    public Like(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorName, IStorage storage) {
        String[] id = getObject().getLitObject().getId().split("/");
        UUID uuid = java.util.UUID.fromString(id[id.length-1]);
        LitObject obj = storage.getObject(uuid);
        if (obj != null) {
            for(LinkOrObject o: obj.getLikes().getOrderedItems()){
                Activity ac = (Activity) o.getLitObject();
                if(ac.getActor().getLitObject().getId().equals(storage.getActor(actorName).getId())){
                    return this;
                }
            }
            obj.setLike(storage.getObject(uuid).getLike()+1);
            obj.getLikes().getOrderedItems().add(new LinkOrObject(this));
            storage.getActor(actorName).getLiked().getOrderedItems().add(new LinkOrObject(obj));

        } else {
            //TODO: Error?
        }
        return this;
    }

    public String getType() {
        return type;
    }
}
