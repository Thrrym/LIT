package de.tuberlin.tkn.lit.model.activities;

import de.tuberlin.tkn.lit.model.*;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

import java.util.ArrayList;
import java.util.List;
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
        String[] id = getObject().getLink().split("/");
        UUID uuid = java.util.UUID.fromString(id[id.length - 1]);
        LitObject obj = storage.getObject(uuid);

        if (obj != null) {
            OrderedCollection col = obj.getLikes();
            if (col != null) {
                for (LinkOrObject o : obj.getLikes().getOrderedItems()) {
                    Activity ac = (Activity) o.getLitObject();
                    Actor actor = storage.getActor(UriUtilities.getActor(ac.getActor().getLink()));
                    if (actor.getId().equals(storage.getActor(actorName).getId())) {
                        return this;
                    }
                }

                obj.getLikes().getOrderedItems().add(new LinkOrObject(this));
            } else {
                List<LinkOrObject> list = new ArrayList<>();
                list.add(new LinkOrObject(this));
                obj.setLikes(new OrderedCollection(list));
            }
            obj.setLike(storage.getObject(uuid).getLike() + 1);
            if (storage.getActor(actorName).getLiked() != null) {
                storage.getActor(actorName).getLiked().getOrderedItems().add(new LinkOrObject(obj));
            } else {
                List<LinkOrObject> list = new ArrayList<>();
                list.add(new LinkOrObject(obj));
                storage.getActor(actorName).setLiked(new OrderedCollection(list));
            }
        }
        return this;
    }

    public String getType() {
        return type;
    }
}
