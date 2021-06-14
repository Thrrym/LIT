package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.*;

import java.util.UUID;

public interface IStorage {
    Actor getActor(String actorName);

    Actor createActor(Actor actor);

    boolean removeActor(Actor actor);

    OrderedCollection getInbox(String actorName);

    OrderedCollection getOutbox(String actorName);

    OrderedCollection getObjectsCreatedByActor(String actorName);

    OrderedCollection getRelevantObjects(String actorName);

    void addToInbox(String actorName, LinkOrObject toAdd);

    void addToOutbox(String actorName, LinkOrObject toAdd);


    Activity getActivity(UUID id);

    Activity createActivity(String actorName, Activity activity);

    LitObject getObject(UUID id);

    LitObject createObject(String actorName, String objectType, LitObject object);

    LitObject updateObject(String actorName, LitObject object);

    OrderedCollection getLikedCollection(String actorName);

    void addToLiked(String actorName, LinkOrObject toAdd);
}
