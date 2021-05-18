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
public class ServerController {

    private final Map<String, Function<Activity, Activity>> activityMap = new HashMap<>();

    public ServerController() {
        activityMap.put("Create", Create::new);
    }

    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.POST)
    public void postInbox(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {
        String activityType = activity.getType();
        Function<Activity, Activity> activityFunc = activityMap.get(activityType);
        Activity res = activityFunc.apply(activity);
    }

    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.GET)
    public OrderedCollection getOutbox(@PathVariable("actorname") String actorname) {
        return null;
    }
}
