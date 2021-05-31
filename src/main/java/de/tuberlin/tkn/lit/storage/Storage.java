package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.OrderedCollection;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class Storage implements IStorage {
    private final Map<String, OrderedCollection> outboxes = new HashMap<>();
    private final Map<String, OrderedCollection> inboxes = new HashMap<>();
    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<UUID, Activity> activities = new HashMap<>();

    @Override
    public Actor getActor(String actorName) {
        return actors.get(actorName);
    }

    @Override
    public OrderedCollection getInbox(String actorName) {
        return inboxes.get(actorName);
    }

    @Override
    public OrderedCollection getOutbox(String actorName) {
        return outboxes.get(actorName);
    }

    @Override
    public void addToInbox(String actorName, LinkOrObject toAdd) {
        inboxes.get(actorName).getOrderedItems().add(toAdd);
    }

    @Override
    public void addToOutbox(String actorName, LinkOrObject toAdd) {
        outboxes.get(actorName).getOrderedItems().add(toAdd);
    }

    @Override
    public Actor createActor(Actor actor) {
        if (actors.get(actor.getName()) != null) {
            return null;
        }
        actors.put(actor.getName(), actor);
        outboxes.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        inboxes.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        return actors.get(actor.getName());
    }

    @Override
    public boolean removeActor(Actor actor) {
        if (actors.get(actor.getName()) == null) {
            return false;
        }
        actors.remove(actor.getName());
        outboxes.remove(actor.getName());
        inboxes.remove(actor.getName());
        return true;
    }

    @Override
    public Activity getActivity(UUID id) {
        return activities.get(id);
    }

    @Override
    public Activity createActivity(String actorName, Activity activity) {
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName}, uuid);
        activity.setId(id);
        activities.put(uuid, activity);
        return activities.get(uuid);
    }
}