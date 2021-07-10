package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.service.IPersonService;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Storage implements IStorage {

    @Autowired
    IPersonService personService;

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


    //# TODO ACTOR

    @Override
    public Actor createActor(Actor actor) {

        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List) personRepository.findAll();
        for(Person p : persons) {
            if (p.getName() == actor.getName()) { return null; }
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

        personRepository.save((Person)actor);

        return actors.get(actor.getName());
    }

    @Override
    public Actor getActor(String actorName) {
        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List) personRepository.findAll();
        for(Person p :  persons) {
            if(p.getName() == actorName) return p;
        }
        throw new NullPointerException();
    }

    @Override
    public boolean removeActor(Actor actor) {
        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List) personRepository.findAll();
        for(Person p :  persons) {
            if(p.getName() == actor.getName()) {
                //# TODO remove actor from inboxes & outboxes
                return true;
            }
        }
        return false;
    }

    @Override
    public ActivityPubCollection getActors() {
        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List) personRepository.findAll();
        return new ActivityPubCollection(persons.stream().map(LinkOrObject::new).collect(Collectors.toList()));
    }

    @Override
    public boolean existsByUsername(String actorName) {
        return actors.get(actorName) != null;
    }

    //# TODO OBJECTS

    @Override
    public ActivityPubObject getObject(String id) {
        return objects.get(id);
    }

    @Override
    public ActivityPubCollection getObjects() {
        return new ActivityPubCollection(objects.values().stream().map(LinkOrObject::new).collect(Collectors.toList()));
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
    public OrderedCollection getObjectsCreatedByActor(String actorName) {
        String actorId = getActor(actorName).getId();
        List<LinkOrObject> results = objects.values().stream().filter(value -> value.getGenerator().getLink().equals(actorId)).map(LinkOrObject::new).collect(Collectors.toList());
        return new OrderedCollection(results);
    }

    @Override
    public OrderedCollection getRelevantObjects(String actorName) {
        return new OrderedCollection(relevantObjects.get(actorName).stream().map((id) -> new LinkOrObject(objects.get(id))).collect(Collectors.toList()));
    }

    public void addToRelevantObjects(String actorName, LinkOrObject toAdd) {
        relevantObjects.get(actorName).add(toAdd.getId());
    }

    //# TODO ACTIVITY
    @Override
    public Activity getActivity(UUID id) {
        return activities.get(id);
    }

    @Override
    public Activity createActivity(String actorName, Activity activity) {
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName}, serverPort, uuid);
        activity.setId(id);
        activities.put(uuid, activity);

        return activities.get(uuid);
    }

    //# TODO LIKED
    @Override
    public OrderedCollection getLikedCollection(String actorName) {
        return new OrderedCollection(liked.get(actorName).stream().map((id) -> new LinkOrObject(objects.get(id))).collect(Collectors.toList()));
    }

    @Override
    public void addToLiked(String actorName, LinkOrObject toAdd) {
        liked.get(actorName).add(toAdd.getId());
    }

    //# TODO FOLLOWING
    @Override
    public OrderedCollection getFollowingCollection(String actorName) {
        return followingCollections.get(actorName);
    }

    @Override
    public void addToFollowing(String actorName, LinkOrObject toAdd) {
        followingCollections.get(actorName).getOrderedItems().add(toAdd);
    }

    //# TODO FOLLOWERS
    @Override
    public OrderedCollection getFollowersCollection(String actorName) {
        return followersCollections.get(actorName);
    }

    @Override
    public void addToFollowers(String actorName, LinkOrObject toAdd) {
        followersCollections.get(actorName).getOrderedItems().add(toAdd);
    }

    //# TODO INBOX
    @Override
    public OrderedCollection getInbox(String actorName) {
        OrderedCollection orderedCollection = inboxes.get(actorName);
        if (orderedCollection == null) {
            throw new NullPointerException();
        }
        return orderedCollection;
    }

    @Override
    public void addToInbox(String actorName, LinkOrObject toAdd) {
        inboxes.get(actorName).getOrderedItems().add(toAdd);
    }

    //# TODO OUTBOX
    @Override
    public OrderedCollection getOutbox(String actorName) {
        return outboxes.get(actorName);
    }

    @Override
    public void addToOutbox(String actorName, LinkOrObject toAdd) {
        outboxes.get(actorName).getOrderedItems().add(toAdd);
    }


}