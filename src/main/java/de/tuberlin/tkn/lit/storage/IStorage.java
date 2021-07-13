package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;

import java.util.Collection;
import java.util.List;

public interface IStorage {
    Collection<ActivityPubObject> getObjectsCollection();

    Collection<Actor> getActorsCollection();

    int getFollowerCount(String actor);

    int getPostCount(String actor);

    Actor getActor(String actorName);

    ActivityPubCollection getActors();

    Actor createActor(Actor actor);

    boolean removeActor(Actor actor);

    boolean existsByUsername(String actorName);

    OrderedCollection getInbox(String actorName);

    OrderedCollection getOffers(String actorName);

    OrderedCollection getOutbox(String actorName);

    List<Activity> getPendingActivities(String url);

    void addPendingActivity(String url, Activity activity);

    List<String> getFederatedHosts();

    OrderedCollection getObjectsCreatedByActor(String actorName);

    OrderedCollection getRelevantObjects(String actorName);

    void addToRelevantObjects(String actorName, LinkOrObject toAdd);

    void addToInbox(String actorName, LinkOrObject toAdd);

    void addToOutbox(String actorName, LinkOrObject toAdd);

    Activity getActivity(String id);

    Activity createActivity(String actorName, Activity activity,boolean generateId);

    ActivityPubObject getObject(String id);

    ActivityPubCollection getObjects();

    ActivityPubCollection getAuthors();

    boolean authorExists(String orcId);

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
