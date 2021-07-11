package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.processing.IActivitySender;
import de.tuberlin.tkn.lit.storage.Storage;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ClientController {

    @Autowired
    IActivitySender activitySender;
    @Autowired
    Storage storage;
    @Value("${server.port}")
    private int serverPort;

    @RequestMapping(value = "/{actor}", method = RequestMethod.GET)
    public Actor getActor(@PathVariable("actor") String actorName) {
        return storage.getActor(actorName);
    }

    @RequestMapping(value = "/actors", method = RequestMethod.GET)
    public ActivityPubCollection getActors() {
        return storage.getActors();
    }

    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.GET)
    public ResponseEntity<OrderedCollection> getInbox(@PathVariable("actorname") String actorname) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        if (!actorname.equals(username)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(storage.getInbox(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/{actorname}/objects", method = RequestMethod.GET)
    public OrderedCollection getObjectsCreatedByActor(@PathVariable("actorname") String actorname) {
        return storage.getObjectsCreatedByActor(actorname);
    }

    @RequestMapping(value = "/objects", method = RequestMethod.GET)
    public ActivityPubCollection getObjects() {
        return storage.getObjects();
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public ActivityPubCollection getAuthors() {
        return storage.getAuthors();
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        if (!actorName.equals(username)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Activity tempActivity = activity.handle(actorName, storage, serverPort);
        if (tempActivity != null) {
            tempActivity.setActor(new LinkOrObject(storage.getActor(actorName).getId()));
            Activity createdActivity = storage.createActivity(actorName, tempActivity);
            storage.addToOutbox(actorName, new LinkOrObject(createdActivity));

            createdActivity.handleSendings(storage, activitySender, serverPort);

            return new ResponseEntity<>(createdActivity.getId(), HttpStatus.CREATED);
        }

        if (activity.getObject().getLitObject() != null && activity.getObject().getLitObject().getType().equals("Tombstone")) {
            return new ResponseEntity<>(HttpStatus.GONE);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{actorname}/liked", method = RequestMethod.GET)
    public OrderedCollection getLiked(@PathVariable("actorname") String actorname) {
        return storage.getLikedCollection(actorname);
    }

    @RequestMapping(value = "/{actorname}/following", method = RequestMethod.GET)
    public OrderedCollection getFollowing(@PathVariable("actorname") String actorname) {
        return storage.getFollowingCollection(actorname);
    }

    @RequestMapping(value = "/{actorname}/followers", method = RequestMethod.GET)
    public OrderedCollection getFollowers(@PathVariable("actorname") String actorname) {
        return storage.getFollowersCollection(actorname);
    }
}
