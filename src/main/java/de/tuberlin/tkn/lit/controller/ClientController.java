package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.OrderedCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ClientController {

    private Map<String, List<Activity>> outboxes;
    private Map<String, OrderedCollection> inboxes;
    // authentication: oauth2 maybe? https://spring.io/guides/tutorials/spring-boot-oauth2/
    //private final Map<String, Function<Activity, ?>> activityMap = new HashMap<>();

    @Autowired
    public ClientController() {
        outboxes = new HashMap<>();
        outboxes.put("testuser01", new ArrayList<>());
        outboxes.put("testuser02", new ArrayList<>());

        inboxes = new HashMap<>();
        inboxes.put("testuser01", new OrderedCollection(new ArrayList<>()));
        inboxes.put("testuser02", new OrderedCollection(new ArrayList<>()));
    }

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.POST)
    public void postActivity(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {

        //STUB START

        if (!outboxes.containsKey(actorname)) {
            outboxes.put(actorname, new ArrayList<>(Collections.singletonList(activity)));
        } else {
            outboxes.get(actorname).add(activity);
        }

        if (actorname.equals("testuser01")) {
            inboxes.get("testuser02").getOrderedItems().add(new LinkOrObject(activity));
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

        return inboxes.get(actorname);

        //STUB END

        // was schicken die uns um den Actor zu identifizieren?
        //return inbox as OrderedCollection
        // Datenbankabfrage von actor dessen Inbox, paging abfrage geregelt von datenbank gruppe, wir geben Page-ID weiter
        // Weitergabe von Inbox an Frontend, paging wir bekommen Page-ID
    }

    @RequestMapping(value = "/{actor}", method = RequestMethod.GET)
    public Actor getActor(@PathVariable("actor") String actor) {
        // ist das so, dass das auch andere Server Aufrufen sollen?
        return null;
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
