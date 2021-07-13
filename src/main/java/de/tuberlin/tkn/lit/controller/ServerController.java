package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.activitypub.activities.*;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.storage.IStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import java.util.ArrayList;

@RestController
public class ServerController {

    // Dependency Injection

    @Autowired
    IStorage storage;

    @Value("${server.port}")
    private int serverPort;

    // Dependency Injection END

    @Autowired
    public ServerController() {

        //STUB START

        // actors are initalized in constructor of ClientController

        //STUB END
    }

    /**
     * Http route for other servers to use, to post activities to an inbox, belonging to
     * an actor, held by this server. Also all side effects of the activity are processed.
     *
     * @param actorname name of the actor whose inbox is the target
     * @param activity  activity to post and process
     */
    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.POST)
    public void postInbox(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {
        System.out.print("post inbox " + activity.getActor().getLink());
        handleActivity(actorname, activity);
    }

    /**
    *  Http route for others servers to use, to retrieve the outbox of a specific actor,
    *  held by this server.
    * @param  actorname name of the actor whose outbox is the target
    * @return the outbox belonging to the actor
    */
    @RequestMapping(value = "/{actorname}/outbox", method = RequestMethod.GET)
    public OrderedCollection getOutbox(@PathVariable("actorname") String actorname) {
        return storage.getOutbox(actorname);
    }

    /**
    *  Http route for others servers to use, to enter the federation.
    * @param  newMember url of the new member
    * @return pending activities
    */
    @RequestMapping(value = "/join-federation", method = RequestMethod.POST)
    public ResponseEntity<List<Activity>> joinFederation(@RequestBody String newMember) {
        // if the newHost is not already known to the storage,
        // it will be added !
        List<Activity> res = storage.getPendingActivities(newMember);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
    *  Http route for others servers to use, to get all members of the federation.
    * @return List of federation member urls.
    */
    @RequestMapping(value = "/federation-members", method = RequestMethod.GET)
    public List<String> federationMembers() {
        List<String> res = storage.getFederatedHosts();
        return res;
    }

    /**
    *  (Helper) Processing an activity for an actors inbox 
	*  (same as post inbox route)
    * @param  actorname
    * @param  activity
    */
    public void handleActivity(String actorname, Activity activity) {

        // TODO reset to property (check if correct reasoning)
        activity.setTo(new ArrayList<>());

        // post activity to the actors inbox
        storage.addToInbox(actorname, new LinkOrObject(activity));

        if (activity instanceof Create) {
            Create createActivity = (Create) activity;
            LinkOrObject toSave = createActivity.getObject();

            storage.createObject(toSave.getId(), toSave.getLitObject());
            storage.addToRelevantObjects(actorname, toSave);
        } else if (activity instanceof Like) {
            activity.handle(activity.getActor().getId(), storage, serverPort);
            storage.addToRelevantObjects(actorname, activity.getObject());
        } else if (activity instanceof Follow) {
            activity.handle(activity.getActor().getId(), storage, serverPort);
        } else if (activity instanceof Update) {
            activity.handle(activity.getActor().getId(), storage, serverPort);
        } else if (activity instanceof Delete) {
            activity.handle(activity.getActor().getId(), storage, serverPort);
        } else if (activity instanceof Offer) {
            storage.createActivity(actorname, activity,false);
            activity.handle(activity.getActor().getId(), storage, serverPort);
        }
        // TODO: notify client
    }
}
