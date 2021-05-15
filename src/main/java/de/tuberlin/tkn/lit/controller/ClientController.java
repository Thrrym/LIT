package de.tuberlin.tkn.lit.controller;
import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.OrderedCollection;
import de.tuberlin.tkn.lit.model.activities.Create;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
public class ClientController {

    // authentication: oauth2 maybe? https://spring.io/guides/tutorials/spring-boot-oauth2/
    private final Map<String, Function<Activity, Activity>> activityMap = new HashMap<>();

    public ClientController() {
        activityMap.put("Create", Create::new);
    }

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.POST)
    public void postActivity(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {
        // schicken die uns das wie EXAMPLE 14?
        // TODO: lege activity in collection und update datenbank
        // activityMap.get(activity.getType());
        if(activity.getType().equals("Create")){ // Das gleiche mit allen anderen Activities
            Create create = new Create(activity);
            // TODO:Datenbank Eintrag mit nur bestimmten Json Attributen
            // TODO:Methode bla(create) von server-server leuten
        }
    }

    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {
        // was schicken die uns um den Actor zu identifizieren?
        //return inbox as OrderedCollection
        // Datenbankabfrage von actor dessen Inbox, paging abfrage geregelt von datenbank gruppe, wir geben Page-ID weiter
        // Weitergabe von Inbox an Frontend, paging wir bekommen Page-ID
    }

    @RequestMapping(value = "/{actor}", method = RequestMethod.GET)
    public Actor getActor(@PathVariable("actor") String actor) {
        // ist das so, dass das auch andere Server Aufrufen sollen?
    }

    @RequestMapping(value = "/{actorname}/following", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

    }

    @RequestMapping(value = "/{actorname}/followers", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

    }

    @RequestMapping(value = "/{actorname}/liked", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {

    }
}
