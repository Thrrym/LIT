package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.Actor;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    // authentication: oauth2 maybe? https://spring.io/guides/tutorials/spring-boot-oauth2/

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.POST)
    public void postActivity(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {
        // schicken die uns das wie EXAMPLE 14?
    }

    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.GET)
    public OrderedCollection getInbox(@PathVariable("actorname") String actorname) {
        // was schicken die uns um den Actor zu identifizieren?
        //return inbox as OrderedCollection
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
