package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.*;
import de.tuberlin.tkn.lit.model.actors.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.tuberlin.tkn.lit.processing.IActivitySender;
import de.tuberlin.tkn.lit.storage.IStorage;

import java.util.*;

@RestController
public class ClientController {

    // STUB PROPERTIES

    // STUB END
	
	// Dependency Injection
	
	@Autowired
	IActivitySender activitySender;
	
	@Autowired
	IStorage storage;
	
	// Dependency Injection END

    // authentication: oauth2 maybe? https://spring.io/guides/tutorials/spring-boot-oauth2/
    //private final Map<String, Function<Activity, ?>> activityMap = new HashMap<>();

    @Autowired
    public ClientController(IStorage storage) {

        //STUB START

    	this.storage = storage;
    	
        this.storage.AddActor(new Person("testuser01"));
        this.storage.AddActor(new Person("testuser02"));

        //STUB END
    }

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.POST)
    public void postActivity(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {

        //STUB START

        OrderedCollection outbox = storage.GetOutbox(actorname);
        outbox.getOrderedItems().add(new LinkOrObject(activity));

        if (actorname.equals("testuser01")) {
            OrderedCollection inbox = storage.GetOutbox("testuser02");
            inbox.getOrderedItems().add(new LinkOrObject(activity));
        }
		else {
			activitySender.send(activity);
		}

        //STUB END


        // TODO: lege activity in collection und update datenbank
        // activityMap.get(activity.getType());
        //if (activity.getType().equals("Create")) { // Das gleiche mit allen anderen Activities
        //  Create create = new Create(activity);
        // TODO:Datenbank Eintrag mit nur bestimmten Json Attributen
        // TODO:Methode bla(create) von server-server leuten
        //}
    }

    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

        //STUB START

        return storage.GetInbox(actorname);

        //STUB END

        //return inbox as OrderedCollection
        // Datenbankabfrage von actor dessen Inbox, paging abfrage geregelt von datenbank gruppe, wir geben Page-ID weiter
        // Weitergabe von Inbox an Frontend, paging wir bekommen Page-ID
    }

    @RequestMapping(value = "/{actor}", method = RequestMethod.GET)
    public Actor getActor(@PathVariable("actor") String actor) {
        // ist das so, dass das auch andere Server Aufrufen sollen?
        return null;
    }

    @RequestMapping(value = "/actor", method = RequestMethod.POST)
    public ResponseEntity<Actor> postActor(@RequestBody Actor actor) {
    	List<Actor> actors = storage.GetActors();
        if (actors.stream().noneMatch(a -> a.getName().equals(actor.getName()))) {
            actor.instantiateFields();
            actors.add(actor);
            return new ResponseEntity<>(actor, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
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
