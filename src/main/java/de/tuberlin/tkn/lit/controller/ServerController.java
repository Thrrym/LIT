package de.tuberlin.tkn.lit.controller;
import de.tuberlin.tkn.lit.model.Activity;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.OrderedCollection;
import de.tuberlin.tkn.lit.model.activities.Create;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
public class ServerController {

    public ServerController() {

    }

    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.POST)
    public void postInbox(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {
        // example access
        System.out.println(activity.getActor().getLink());
        LinkOrObject toZero = activity.getTo().get(0);
        LinkOrObject toOne = activity.getTo().get(1);
        System.out.println(toZero.getLink());
        System.out.println(toOne.getLink());
        // example access end
    }

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.GET)
    public OrderedCollection getOutbox(@PathVariable("actorname") String actorname) {
        return null;
    }
}
