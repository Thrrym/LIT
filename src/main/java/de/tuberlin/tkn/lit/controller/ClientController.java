package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.OrderedCollection;
import de.tuberlin.tkn.lit.model.actors.Person;
import de.tuberlin.tkn.lit.processing.IActivitySender;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    // STUB PROPERTIES

    // STUB END

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

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.POST)
    public void postActivity(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {

        // TODO: Check if all actors are present

        //STUB START

        OrderedCollection outbox = storage.getOutbox(actorname);
        activity.setId(new String[]{actorname}, true);
        outbox.getOrderedItems().add(new LinkOrObject(activity));

        if (activity.getTo() != null) {
            for (LinkOrObject linkOrObject : activity.getTo()) {
                if (UriUtilities.isLocaleServer(linkOrObject.getLink())) {
                    OrderedCollection inbox = storage.getInbox(UriUtilities.getActor(linkOrObject.getLink()));
                    inbox.getOrderedItems().add(new LinkOrObject(activity));
                } else {
                    activitySender.send(activity);
                }
            }
        }

        if (activity.getCc() != null) {
            for (LinkOrObject linkOrObject : activity.getCc()) {
                if (UriUtilities.isLocaleServer(linkOrObject.getLink())) {
                    OrderedCollection inbox = storage.getInbox(UriUtilities.getActor(linkOrObject.getLink()));
                    inbox.getOrderedItems().add(new LinkOrObject(activity));
                } else {
                    activitySender.send(activity);
                }
            }
        }

        //STUB END
    }

    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

        //STUB START

        return storage.getInbox(actorname);

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
