package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;

import java.util.UUID;

public interface IStorage {
    Actor getActor(String actorName);

    Actor createActor(Actor actor);

    boolean removeActor(Actor actor);

    OrderedCollection getInbox(String actorName);

    OrderedCollection getOutbox(String actorName);

    OrderedCollection getObjectsCreatedByActor(String actorName);

    OrderedCollection getRelevantObjects(String actorName);

    void addToRelevantObjects(String actorName, LinkOrObject toAdd);

    void addToInbox(String actorName, LinkOrObject toAdd);

    void addToOutbox(String actorName, LinkOrObject toAdd);

    Activity getActivity(UUID id);

    Activity createActivity(String actorName, Activity activity);

    ActivityPubObject getObject(String id);

    ActivityPubObject createObject(String actorName, String objectType, ActivityPubObject object);

    ActivityPubObject createObject(String id, ActivityPubObject object);

    ActivityPubObject updateObject(String actorName, ActivityPubObject object);

    OrderedCollection getLikedCollection(String actorName);

    void addToLiked(String actorName, LinkOrObject toAdd);

    void addToFollowers(String actorName, LinkOrObject toAdd);

    OrderedCollection getFollowersCollection(String actorName);

    void addToFollowing(String actorName, LinkOrObject toAdd);

    OrderedCollection getFollowingCollection(String actorName);
}
