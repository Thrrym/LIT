package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import org.springframework.core.annotation.Order;

import java.util.UUID;

public interface IStorage {
    Actor getActor(String actorName);

    ActivityPubCollection getActors();

    Actor createActor(Actor actor);

    boolean removeActor(Actor actor);

    boolean existsByUsername(String actorName);

    OrderedCollection getInbox(String actorName);

    OrderedCollection getOffers(String actorName);

    OrderedCollection getOutbox(String actorName);

    OrderedCollection getObjectsCreatedByActor(String actorName);

    OrderedCollection getRelevantObjects(String actorName);

    void addToRelevantObjects(String actorName, LinkOrObject toAdd);

    void addToInbox(String actorName, LinkOrObject toAdd);

    void addToOutbox(String actorName, LinkOrObject toAdd);

    Activity getActivity(String id);

    Activity createActivity(String actorName, Activity activity);

    ActivityPubObject getObject(String id);

    ActivityPubCollection getObjects();

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
