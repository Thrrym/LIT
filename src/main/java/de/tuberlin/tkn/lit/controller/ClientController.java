package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.*;
import de.tuberlin.tkn.lit.model.activities.Create;
import de.tuberlin.tkn.lit.model.actors.Person;
import de.tuberlin.tkn.lit.processing.IActivitySender;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ClientController {

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

    @RequestMapping(value = "/{actorname}/{id}", method = RequestMethod.GET)
    public Activity getActivity(@PathVariable("actorname") String actorname, @PathVariable("id") UUID id) {
        return storage.getActivity(id);
    }

    @RequestMapping(value = "/{actorname}/{objecttype}/{id}", method = RequestMethod.GET)
    public LitObject getObject(@PathVariable("actorname") String actorname, @PathVariable("objecttype") String objectType, @PathVariable("id") UUID id) {
        return storage.getObject(id);
    }

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.POST)
    public ResponseEntity<String> postActivity(@PathVariable("actorname") String actorName, @RequestBody Activity activity) {

        Activity createdActivity = storage.createActivity(actorName, activity.handle(actorName, activity, storage));
        storage.addToOutbox(actorName, new LinkOrObject(createdActivity));

        if (createdActivity.getTo() != null) {
            for (LinkOrObject linkOrObject : createdActivity.getTo()) {
                if (UriUtilities.isLocaleServer(linkOrObject.getLink())) {
                    OrderedCollection inbox = storage.getInbox(UriUtilities.getActor(linkOrObject.getLink()));
                    inbox.getOrderedItems().add(new LinkOrObject(createdActivity));
                } else {
                    activitySender.send(createdActivity);
                }
            }
        }

        if (createdActivity.getCc() != null) {
            for (LinkOrObject linkOrObject : createdActivity.getCc()) {
                if (UriUtilities.isLocaleServer(linkOrObject.getLink())) {
                    OrderedCollection inbox = storage.getInbox(UriUtilities.getActor(linkOrObject.getLink()));
                    inbox.getOrderedItems().add(new LinkOrObject(createdActivity));
                } else {
                    activitySender.send(createdActivity);
                }
            }
        }

        return new ResponseEntity<>(createdActivity.getId(), HttpStatus.CREATED);
    }

    /*@RequestMapping(value = "/{actorname}/following", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

    }

    @RequestMapping(value = "/{actorname}/followers", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

    }

    @RequestMapping(value = "/{actorname}/liked", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

    }*/
}
