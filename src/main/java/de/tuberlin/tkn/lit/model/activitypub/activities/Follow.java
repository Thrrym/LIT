package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.storage.IStorage;

import java.util.ArrayList;
import java.util.List;

public class Follow extends Activity {
    private static final String type = "Follow";

    public Follow() {
    }

    public Follow(Activity activity) {
        super(activity);
    }

    @Override
    public Activity handle(String actorId, IStorage storage, int port) {
        if (!actorId.startsWith("http"))
            actorId = storage.getActor(actorId).getId();
        // Get actor to follow
        ActivityPubObject obj = getObject().getLitObject();
        // TODO: Check if toFollowing actor exists
        Actor actor = (Actor) obj;
            if (!storage.getFollowingCollection(actorId).getOrderedItems().contains(actor))
                storage.addToFollowing(actorId, new LinkOrObject(actor));

        // Add to other actors follower list, if available in storage
        ActivityPubObject obj_toFollow = storage.getObject(getObject().getId());
        if (obj_toFollow != null)
            storage.addToFollowers(actor.getName(), getActor());
        else {
            // TODO: Inform server-server
        }
        return this;
    }

        public String getType () {
            return type;
        }
}