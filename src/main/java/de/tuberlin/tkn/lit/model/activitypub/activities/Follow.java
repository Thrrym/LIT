package de.tuberlin.tkn.lit.model.activitypub.activities;

import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

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
        if (getActor().isObject()){
            getActor().getLitObject().setId(storage.getActor(getActor().getLitObject().getName()).getId());
        }

        ActivityPubObject obj = getObject().getLitObject();
        Actor actor = (Actor) obj;
        // Outbox
        if (!actorId.startsWith("http")) {
            //actorId = storage.getActor(actorId).getId();
            // Get actor to follow
           // ActivityPubObject obj = getObject().getLitObject();
            // TODO: Check if toFollowing actor exists
           // Actor actor = (Actor) obj;
            // TODO
            if (!storage.getFollowingCollection(actorId).getOrderedItems().contains(actor))
                storage.addToFollowing(actorId, new LinkOrObject(actor));
            else {
                return this;
            }

            // TODO: Inform server-server (inbox of actor to follow)
            List<LinkOrObject> customTo = new ArrayList<>();
            // Actor (object) must be a link
            customTo.add(new LinkOrObject(getObject().getLink()));
            //setTo(customTo);

// TODO link or object
            if(UriUtilities.isLocaleServer(getTo().get(0).getLink(), port)) {
                if (getObject().isObject()) {
                    getObject().getLitObject().setId(storage.getActor(getObject().getLitObject().getName()).getId());
                }
                // storage.addToFollowers(UriUtilities.getActor(getTo().get(0).getLink()), getActor());
                storage.addToFollowers(UriUtilities.getActor(getObject().getId()), new LinkOrObject(storage.getActor(UriUtilities.getActor(getActor().getId())).getId()));
            }

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