package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.service.IPersonService;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Storage implements IStorage {

    @Autowired
    private IPersonService personService;

    private final Map<String, OrderedCollection> outboxes = new HashMap<>();
    private final Map<String, OrderedCollection> inboxes = new HashMap<>();
    private final Map<String, OrderedCollection> followingCollections = new HashMap<>();
    private final Map<String, OrderedCollection> followersCollections = new HashMap<>();
    private final Map<String, Set<String>> relevantObjects = new HashMap<>();
    private final Map<String, Set<String>> liked = new HashMap<>();
    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<UUID, Activity> activities = new HashMap<>();
    private final Map<String, ActivityPubObject> objects = new HashMap<>();
    @Value("${server.port}")
    private int serverPort;

    @Override
    public Actor getActor(String actorName) {
        PersonRepository personRepo = personService.getRepository();
        List<Person> xPersons = (List<Person>) personRepo.findAll();
        for(Person person : xPersons) {
            if(person.getName().equals(actorName))  {
                return person;
            }
        }
        throw new NullPointerException();
/*
        Actor actor = actors.get(actorName);
        if (actor == null) {
            throw new NullPointerException();
        }
*/
//        return null;
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
    public OrderedCollection getOutbox(String actorName) {
        return outboxes.get(actorName);
    }


    @Override
    public OrderedCollection getObjectsCreatedByActor(String actorName) {
        String actorId = getActor(actorName).getAp_id();
        List<LinkOrObject> results = objects.values().stream().filter(value -> value.getGenerator().getLink().equals(actorId)).map(LinkOrObject::new).collect(Collectors.toList());
        return new OrderedCollection(results);
    }

    @Override
    public OrderedCollection getRelevantObjects(String actorName) {
        return new OrderedCollection(relevantObjects.get(actorName).stream().map((id) -> new LinkOrObject(objects.get(id))).collect(Collectors.toList()));
    }

    public void addToRelevantObjects(String actorName, LinkOrObject toAdd){
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
        PersonRepository personRepo = personService.getRepository();

        if (actors.get(actor.getName()) != null) {
            return null;
        }
        actor.setAp_id(UriUtilities.generateId(new String[]{actor.getName()}, serverPort, false));
        actor.setInbox(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.INBOX}, serverPort, false));
        actor.setOutbox(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.OUTBOX}, serverPort, false));
        actor.setFollowers(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.FOLLOWERS}, serverPort, false));
        actor.setFollowing(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.FOLLOWING}, serverPort, false));
        actor.setLiked(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.LIKED}, serverPort, false));
        actors.put(actor.getName(), actor);
        outboxes.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        inboxes.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        relevantObjects.put(actor.getName(), new HashSet<>());
        liked.put(actor.getName(), new HashSet<>());
        personRepo.save((Person) actor);
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

        PersonRepository personRepo = personService.getRepository();
        personRepo.delete((Person) actor);
        return true;
    }

    @Override
    public Activity getActivity(UUID id) {
        return activities.get(id);
    }

    @Override
    public Activity createActivity(String actorName, Activity activity) {
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName}, serverPort, uuid);
        activity.setAp_id(id);
        activities.put(uuid, activity);
        return activities.get(uuid);
    }

    @Override
    public ActivityPubObject getObject(String id) {
        return objects.get(id);
    }

    @Override
    public ActivityPubObject createObject(String actorName, String objectType, ActivityPubObject object) {
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName, objectType}, serverPort, uuid);
        object.setAp_id(id);
        String actorId = getActor(actorName).getAp_id();
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
        String id = object.getAp_id();
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
    public OrderedCollection getFollowersCollection(String actorName) {
        return followersCollections.get(actorName);
    }

    @Override
    public void addToFollowers(String actorName, LinkOrObject toAdd){
        followersCollections.get(actorName).getOrderedItems().add(toAdd);
    }
}