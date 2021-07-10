package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "FollowActivity")
public class Follow extends Activity {
    private static final String type = "Follow";

    public Follow() {
    }

    public Follow(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorId, IStorage storage, int port) {
        if (UriUtilities.isLocaleServer(getActor().getId(), port)) {
            if (getActor().isObject()) {
                getActor().getLitObject().setId(storage.getActor(getActor().getLitObject().getName()).getId());
            }

            if (!actorId.startsWith("http")) {
                if (!storage.getFollowingCollection(actorId).getOrderedItems().contains(getObject().getId()))
                    storage.addToFollowing(actorId, new LinkOrObject(getObject().getId()));
                else {
                    return this;
                }

                List<LinkOrObject> customTo = getTo();
                if (customTo != null) {
                    customTo.add(new LinkOrObject(getObject().getLink()));
                } else {
                    List<LinkOrObject> temp = new ArrayList<>();
                    temp.add(new LinkOrObject(getObject().getLink()));
                    setTo(temp);
                }
            }
        }

        if (UriUtilities.isLocaleServer(getObject().getId(), port)) {
            if (getObject().isObject()) {
                getObject().getLitObject().setId(storage.getActor(getObject().getLitObject().getName()).getId());
            }
            storage.addToFollowers(UriUtilities.getActor(getObject().getId()), new LinkOrObject(getActor().getId()));
        }

        return this;
    }

    public String getType() {
        return type;
    }
}