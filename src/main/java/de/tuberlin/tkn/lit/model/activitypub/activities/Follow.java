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
        ActivityPubObject obj = getObject().getLitObject();
        Actor actor = (Actor) obj;
        // Outbox
        if (!actorId.startsWith("http")) {
            actorId = storage.getActor(actorId).getId();
            // Get actor to follow
           // ActivityPubObject obj = getObject().getLitObject();
            // TODO: Check if toFollowing actor exists
           // Actor actor = (Actor) obj;
            if (!storage.getFollowingCollection(actorId).getOrderedItems().contains(actor))
                storage.addToFollowing(actorId, new LinkOrObject(actor));
            else {
                return this;
            }

            // TODO: Inform server-server (inbox of actor to follow)
            List<LinkOrObject> customTo = new ArrayList<>();
            // Actor (object) must be a link
            customTo.add(new LinkOrObject(getObject().getLink()));
            setTo(customTo);

            // Inbox
        } else {
            // Add to other actors follower list, if available in storage
            ActivityPubObject objToFollow = storage.getObject(getObject().getId());
            if (objToFollow != null)
                storage.addToFollowers(actor.getName(), getActor());
        }
        return this;
    }

        public String getType () {
            return type;
        }
}