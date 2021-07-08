package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.constants.IActivityConstants;
import de.tuberlin.tkn.lit.constants.ILitObjectConstants;
import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.service.*;
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
    @Autowired
    private IAuthorService authorService;
    @Autowired
    private IActivityPubCollectionService activityPubCollectionService;
    @Autowired
    private IBibTeXArticleService bibTeXArticleService;

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

        IAuthorRepository authorRepo = authorService.getRepository();
        List<Author> authors = (List<Author>) authorRepo.findAll();
        System.out.print("HELLO");

        IPersonRepository personRepo = getPersonService().getRepository();
        List<Person> persons = (List<Person>) personRepo.findAll();
        for(Person p : persons) {
            if(p.getName().equals(actorName)) return p;
        }

        throw new NullPointerException();
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
        liked.put(actor.getName(), new HashSet<>());
        IPersonRepository personRepo = getPersonService().getRepository();
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
        IPersonRepository personRepo = getPersonService().getRepository();
        personRepo.delete((Person) actor);
        return true;
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
        String actorId = getActor(actorName).getId();
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
    public Activity getActivity(UUID id) {
        return activities.get(id);
    }

    @Override
    public Activity createActivity(String actorName, Activity activity) {
        String type = activity.getType();
        //# TODO Fallunterscheidung Activities
        if(type.equals(IActivityConstants.ACCEPT)) {

        }
        if(type.equals(IActivityConstants.BLOCK)) {

        }
        if(type.equals(IActivityConstants.CREATE)) {

        }
        if(type.equals(IActivityConstants.DELETE)) {

        }
        if(type.equals(IActivityConstants.DISLIKE)) {

        }
        if(type.equals(IActivityConstants.FOLLOW)) {

        }
        if(type.equals(IActivityConstants.IGNORE)) {

        }
        if(type.equals(IActivityConstants.LIKE)) {

        }
        if(type.equals(IActivityConstants.REJECT)) {

        }
        if(type.equals(IActivityConstants.UNDO)) {

        }
        if(type.equals(IActivityConstants.UPDATE)) {

        }
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName}, serverPort, uuid);
        activity.setId(id);
        activities.put(uuid, activity);
        return activities.get(uuid);
    }

    @Override
    public ActivityPubObject getObject(String id) {
        return objects.get(id);
    }

    @Override
    public ActivityPubObject createObject(String actorName, String objectType, ActivityPubObject object) {

        //# TODO Fallunterscheidung Activities
        if(objectType.equals(ILitObjectConstants.AUTHOR)) {

        }
        if(objectType.equals(ILitObjectConstants.PAPER)) {

        }
        if(objectType.equals(ILitObjectConstants.BIBTEXARTICLE)) {

        }
        if(objectType.equals(ILitObjectConstants.BOOK)) {

        }
        if(objectType.equals(ILitObjectConstants.JOURNAL)) {

        }
        if(objectType.equals(ILitObjectConstants.ACTIVITYPUBCOLLECTION)) {

        }


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
    public OrderedCollection getFollowersCollection(String actorName) {
        return followersCollections.get(actorName);
    }

    @Override
    public void addToFollowers(String actorName, LinkOrObject toAdd){
        followersCollections.get(actorName).getOrderedItems().add(toAdd);
    }

    public IPersonService getPersonService() {
        return personService;
    }
}