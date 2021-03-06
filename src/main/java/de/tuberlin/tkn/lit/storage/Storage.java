package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.model.activitypub.objects.Tombstone;
import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Storage implements IStorage {

    private final Map<String, OrderedCollection> outboxes = new HashMap<>();
    private final Map<String, OrderedCollection> inboxes = new HashMap<>();
    private final Map<String, OrderedCollection> followingCollections = new HashMap<>();
    private final Map<String, OrderedCollection> followersCollections = new HashMap<>();
    private final Map<String, Set<String>> relevantObjects = new HashMap<>();
    private final Map<String, Set<String>> liked = new HashMap<>();
    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<String, Activity> activities = new HashMap<>();
    private final Map<String, ActivityPubObject> objects = new HashMap<>();
    private final Map<String, List<Activity>> federation = new HashMap<>(); // other server uris as keys to pending send tasks

    @Value("${server.port}")
    private int serverPort;

    @Override
    public Actor getActor(String actorName) {
        Actor actor = actors.get(actorName);
        if (actor == null) {
            throw new NullPointerException();
        }

        return actor;
    }

    @Override
    public Collection<ActivityPubObject> getObjectsCollection() {
        return objects.values();
    }

    @Override
    public Collection<Actor> getActorsCollection() {
        return actors.values();
    }

    @Override
    public int getFollowerCount(String actor) {
        return getFollowersCollection(actor).getTotalItems();
    }

    @Override
    public int getPostCount(String actor) {
        return getObjectsCreatedByActor(actor).getTotalItems();
    }

    @Override
    public ActivityPubCollection getActors() {
        return new ActivityPubCollection(actors.values().stream().map(LinkOrObject::new).collect(Collectors.toList()));
    }

    @Override
    public boolean existsByUsername(String actorName) {
        return actors.get(actorName) != null;
    }

    @Override
    public OrderedCollection getInbox(String actorName) {
        OrderedCollection orderedCollection = inboxes.get(actorName);
        if (orderedCollection == null) {
            throw new NullPointerException();
        }
        return orderedCollection;
    }

    @Override
    public OrderedCollection getOffers(String actorName) {
        OrderedCollection orderedCollection = inboxes.get(actorName);
        if (orderedCollection == null) {
            throw new NullPointerException();
        }

        List<LinkOrObject> inboxItems = orderedCollection.getOrderedItems();

        return new OrderedCollection(inboxItems.stream().filter(item -> item.getLitObject().getType().equals("Offer")).collect(Collectors.toList()));
    }

    @Override
    public OrderedCollection getOutbox(String actorName) {
        return outboxes.get(actorName);
    }

    @Override
    public List<Activity> getPendingActivities(String url) {
        if (federation.containsKey(url)) {
            List<Activity> toReturn = federation.get(url);
            federation.replace(url, new ArrayList<Activity>());
            return toReturn;
        } else {
            List<Activity> l = new ArrayList<Activity>();
            federation.put(url, l);
            return l;
        }
    }

    @Override
    public void addPendingActivity(String url, Activity activity) {
        if (federation.containsKey(url)) {
            List<Activity> l = federation.get(url);
            l.add(activity);
            federation.replace(url, l);
        } else {
            List<Activity> l = new ArrayList<Activity>();
            l.add(activity);
            federation.put(url, l);
        }

        System.out.print("Add activity as pending for " + url + "\n");
    }

    @Override
    public List<String> getFederatedHosts() {
        List<String> federatedHosts = new ArrayList<>(federation.keySet());
        return federatedHosts;
    }

    @Override
    public OrderedCollection getObjectsCreatedByActor(String actorName) {
        String actorId = getActor(actorName).getId();
        List<LinkOrObject> results = objects.values().stream().filter(value -> !(value instanceof Tombstone) && !(value instanceof Author) && value.getGenerator().getLink().equals(actorId)).map(LinkOrObject::new).collect(Collectors.toList());
        return new OrderedCollection(results);
    }

    @Override
    public OrderedCollection getRelevantObjects(String actorName) {
        return new OrderedCollection(relevantObjects.get(actorName).stream().map((id) -> new LinkOrObject(objects.get(id))).filter(value -> !(value.getLitObject() instanceof Tombstone)).collect(Collectors.toList()));
    }

    public void addToRelevantObjects(String actorName, LinkOrObject toAdd) {
        relevantObjects.get(actorName).add(toAdd.getId());
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

        actor.setId(UriUtilities.generateId(new String[]{actor.getName()}, serverPort, false));
        actor.setInbox(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.INBOX}, serverPort, false));
        actor.setOutbox(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.OUTBOX}, serverPort, false));
        actor.setFollowers(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.FOLLOWERS}, serverPort, false));
        actor.setFollowing(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.FOLLOWING}, serverPort, false));
        actor.setLiked(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.LIKED}, serverPort, false));
        actors.put(actor.getName(), actor);
        outboxes.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        inboxes.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        relevantObjects.put(actor.getName(), new HashSet<>());
        followingCollections.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        followersCollections.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        liked.put(actor.getName(), new HashSet<>());

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
    public Activity getActivity(String id) {
        return activities.get(id);
    }

    @Override
    public Activity createActivity(String actorName, Activity activity, boolean generateId) {
        if (generateId) {
            UUID uuid = UUID.randomUUID();
            String id = UriUtilities.generateId(new String[]{actorName}, serverPort, uuid);
            activity.setId(id);
            activities.put(id, activity);

            return activities.get(id);
        } else {
            activities.put(activity.getId(), activity);

            return activities.get(activity.getId());
        }
    }

    @Override
    public ActivityPubObject getObject(String id) {
        return objects.get(id);
    }

    @Override
    public ActivityPubCollection getObjects() {
        return new ActivityPubCollection(objects.values().stream().map(LinkOrObject::new).collect(Collectors.toList()));
    }

    @Override
    public ActivityPubCollection getAuthors() {
        return new ActivityPubCollection(objects.values().stream().filter(o -> o instanceof Author).map(LinkOrObject::new).collect(Collectors.toList()));
    }

    @Override
    public boolean authorExists(String orcId) {
        List<LinkOrObject> duplicates = getAuthors().getItems().stream().filter(a -> ((Author) a.getLitObject()).getOrcid().equals(orcId)).collect(Collectors.toList());
        return !duplicates.isEmpty();
    }

    @Override
    public ActivityPubObject createObject(String actorName, String objectType, ActivityPubObject object) {
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName, objectType}, serverPort, uuid);
        object.setId(id);
        String actorId = getActor(actorName).getId();
        object.setGenerator(new LinkOrObject(actorId));
        objects.put(id, object);

        return objects.get(id);
    }

    @Override
    public ActivityPubObject createObject(String id, ActivityPubObject object) {
        objects.put(id, object);

        return objects.get(id);
    }

    @Override
    public ActivityPubObject updateObject(String actorName, ActivityPubObject object) {
        String id = object.getId();
        objects.put(id, object);

        return objects.get(id);
    }

    @Override
    public OrderedCollection getLikedCollection(String actorName) {
        return new OrderedCollection(liked.get(actorName).stream().map((id) -> new LinkOrObject(objects.get(id))).collect(Collectors.toList()));
    }

    @Override
    public void addToLiked(String actorName, LinkOrObject toAdd) {
        liked.get(actorName).add(toAdd.getId());
    }

    @Override
    public OrderedCollection getFollowingCollection(String actorName) {
        return followingCollections.get(actorName);
    }

    @Override
    public void addToFollowing(String actorName, LinkOrObject toAdd) {
        followingCollections.get(actorName).getOrderedItems().add(toAdd);
    }

    @Override
    public OrderedCollection getFollowersCollection(String actorName) {
        return followersCollections.get(actorName);
    }

    @Override
    public void addToFollowers(String actorName, LinkOrObject toAdd) {
        followersCollections.get(actorName).getOrderedItems().add(toAdd);
    }
}