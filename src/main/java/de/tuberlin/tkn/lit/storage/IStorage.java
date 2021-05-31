package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.OrderedCollection;

import java.util.UUID;

public interface IStorage {
    Actor getActor(String actorName);

    Actor createActor(Actor actor);

    boolean removeActor(Actor actor);

    OrderedCollection getInbox(String actorName);

    OrderedCollection getOutbox(String actorName);

    void addToInbox(String actorName, LinkOrObject toAdd);

    void addToOutbox(String actorName, LinkOrObject toAdd);

    Activity getActivity(UUID id);

    Activity createActivity(String actorName, Activity activity);
}
