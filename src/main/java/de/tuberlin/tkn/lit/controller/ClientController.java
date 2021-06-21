package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.processing.IActivitySender;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ClientController {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    IActivitySender activitySender;

    @Autowired
    IStorage storage;

    @Autowired
    public ClientController(IStorage storage) {

        //STUB START

        this.storage = storage;

        this.storage.createActor(new Person("testuser01"));
        this.storage.createActor(new Person("testuser02"));

        //STUB END
    }

    @RequestMapping(value = "/{actor}", method = RequestMethod.GET)
    public Actor getActor(@PathVariable("actor") String actorName) {

        return storage.getActor(actorName);
    }

    @RequestMapping(value = "/actor", method = RequestMethod.POST)
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        Actor newActor = storage.createActor(actor);
        if (newActor != null) {
            return new ResponseEntity<>(newActor, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {
        return storage.getInbox(actorname);
    }

    @RequestMapping(value = "/{actorname}/objects", method = RequestMethod.GET)
    public OrderedCollection getObjectsCreatedByActor(@PathVariable("actorname") String actorname) {
        return storage.getObjectsCreatedByActor(actorname);
    }

    @RequestMapping(value = "/{actorname}/relevantobjects", method = RequestMethod.GET)
    public OrderedCollection getRelevantObjects(@PathVariable("actorname") String actorname) {
        return storage.getRelevantObjects(actorname);
    }

    @RequestMapping(value = "/{actorname}/{id}", method = RequestMethod.GET)
    public Activity getActivity(@PathVariable("actorname") String actorname, @PathVariable("id") UUID id) {
        return storage.getActivity(id);
    }

    @RequestMapping(value = "/{actorname}/{objecttype}/{id}", method = RequestMethod.GET)
    public ActivityPubObject getObject(@PathVariable("actorname") String actorname, @PathVariable("objecttype") String objectType, @PathVariable("id") UUID id) {
        return storage.getObject(UriUtilities.generateId(new String[]{actorname, objectType}, serverPort, id));
    }

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.POST)
    public ResponseEntity<String> postActivity(@PathVariable("actorname") String actorName, @RequestBody Activity activity) {

        Activity createdActivity = storage.createActivity(actorName, activity.handle(actorName, storage,serverPort));
        storage.addToOutbox(actorName, new LinkOrObject(createdActivity));

        createdActivity.handleSendings(storage, activitySender,serverPort);

        return new ResponseEntity<>(createdActivity.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{actorname}/liked", method = RequestMethod.GET)
    public OrderedCollection getLiked(@PathVariable("actorname") String actorname) {
        return storage.getLikedCollection(actorname);
    }

   /* @RequestMapping(value = "/{actorname}/liked", method = RequestMethod.GET)
    public OrderedCollection getLikeCollection(@PathVariable("actorname") String actorname) {
        return storage.likeCollection(actorname);
    }

    @RequestMapping(value = "/{actorname}/following", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

    }

    @RequestMapping(value = "/{actorname}/followers", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

    }*/


}
