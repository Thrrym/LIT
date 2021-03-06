package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.model.Requests.SearchRequest;
import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.model.lit.BibTeXArticle;
import de.tuberlin.tkn.lit.processing.IFederationClient;
import de.tuberlin.tkn.lit.storage.Storage;
import de.tuberlin.tkn.lit.util.DamerauLevenshteinDistance;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class ClientController {

    @Autowired
    IFederationClient federationClient;

    @Autowired
    Storage storage;
    @Value("${server.port}")
    private int serverPort;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestBody SearchRequest sRequest) {
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(sRequest.getQuery().toLowerCase());
        int q_len = sRequest.getQuery().length();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root_node = mapper.createObjectNode();
        ArrayNode items_array = mapper.createArrayNode();
        root_node.set("items", items_array);

        if (sRequest.getSearchType().equals("literature")) {
            for (ActivityPubObject literature : storage.getObjectsCollection()) {
                if (literature.getType().equals("bibtex_article")) {

                    System.out.println();

                    /**if (distance.getDistanceTo(((BibTeXArticle) literature).getTitle().toLowerCase()) / (float)q_len < 0.5) {
                        ObjectNode actor_result = mapper.createObjectNode();
                        actor_result.put("title", ((BibTeXArticle) literature).getTitle());
                        actor_result.put("likes", ((BibTeXArticle) literature).getLikes());
                        items_array.add(actor_result);
                    }*/

                    float error = 1;
                    String name = "";

                    for (int i = 0; i < ((BibTeXArticle) literature).getTitle().length(); i++) {
                        name = ((BibTeXArticle) literature).getTitle().substring(i);
                        error = Math.min((float)distance.getDistanceTo(name.toLowerCase()) / (float)q_len, error);

                        if (error < 0.5) {
                            ObjectNode actor_result = mapper.createObjectNode();
                            actor_result.put("title", ((BibTeXArticle) literature).getTitle());
                            actor_result.put("type", "Article");
                            actor_result.put("likes", ((BibTeXArticle) literature).getLikes());
                            actor_result.put("generator", UriUtilities.getActor(literature.getGenerator().getId()));
                            items_array.add(actor_result);
                            break;
                        }
                    }
                }
            }
        }
        else if (sRequest.getSearchType().equals("user")) {
            for (Actor actor :  storage.getActorsCollection()) {
                float error = 1;
                String name = "";

                for (int i = 0; i < actor.getName().length(); i++) {
                    name = actor.getName().substring(i);
                    error = Math.min((float)distance.getDistanceTo(name.toLowerCase()) / (float)q_len, error);

                    if (error < 0.5) {
                        ObjectNode actor_result = mapper.createObjectNode();
                        actor_result.put("id", actor.getId());
                        actor_result.put("name", actor.getName());
                        actor_result.put("follower", storage.getFollowerCount(actor.getName()));
                        actor_result.put("posts", storage.getPostCount(actor.getName()));
                        items_array.add(actor_result);
                        break;
                    }
                }
            }
        }

        root_node.put("totalItems", items_array.size());
        root_node.put("searchType", sRequest.getSearchType());

        return root_node.toPrettyString();
    }

    @RequestMapping(value = "/{actor}", method = RequestMethod.GET)
    public Actor getActor(@PathVariable("actor") String actorName) {
        return storage.getActor(actorName);
    }

    @RequestMapping(value = "/actors", method = RequestMethod.GET)
    public ActivityPubCollection getActors() {
        return storage.getActors();
    }

    @RequestMapping(value = "/actorids", method = RequestMethod.GET)
    public List<String> getActorsList(){
        ActivityPubCollection actors_collection = storage.getActors();

        // Extract name from each actor in Collection
        return actors_collection.getItems().stream().map(item -> item.getId()).collect(Collectors.toList());
    }

    @RequestMapping(value = "/all-actors", method = RequestMethod.GET)
    public ActivityPubCollection getAllActors() {
        List<LinkOrObject> local_actors = storage.getActors().getItems();
        // Convert actor names to Person objects for use in ActivityPubCollection
        List<LinkOrObject> remote_actors = federationClient.getRemoteActors(storage).stream().map(x -> new LinkOrObject(new Person(x))).collect(Collectors.toList());

        // Concatenate collections
        local_actors.addAll(remote_actors);

        return new ActivityPubCollection(local_actors);
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

    @RequestMapping(value = "/{actorname}/offers", method = RequestMethod.GET)
    public ResponseEntity<OrderedCollection> getOffers(@PathVariable("actorname") String actorname) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        if (!actorname.equals(username)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        OrderedCollection offers = storage.getOffers(actorname);

        return new ResponseEntity<>(offers, HttpStatus.OK);
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
        return storage.getActivity(UriUtilities.generateId(new String[]{actorname}, serverPort, id));
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

        activity.setActor(new LinkOrObject(storage.getActor(actorName).getId()));

        Activity tempActivity = activity.handle(actorName, storage, serverPort);
        if (tempActivity != null) {
            if (!tempActivity.getType().equals(activity.getType())) {
                tempActivity.handle(actorName, storage, serverPort);

                storage.addToOutbox(actorName, new LinkOrObject(activity));
                storage.addToOutbox(actorName, new LinkOrObject(tempActivity));

                activity.handleSendings(storage, federationClient, serverPort);
                tempActivity.handleSendings(storage, federationClient, serverPort);

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                Activity createdActivity = storage.createActivity(actorName, tempActivity, true);
                storage.addToOutbox(actorName, new LinkOrObject(createdActivity));

                createdActivity.handleSendings(storage, federationClient, serverPort);

                return new ResponseEntity<>(createdActivity.getId(), HttpStatus.CREATED);
            }
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
