package de.tuberlin.tkn.lit.controller;

import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.model.*;
import de.tuberlin.tkn.lit.model.activities.*;
import de.tuberlin.tkn.lit.model.litobjects.BibTeXArticle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    *  Http route for other servers to use, to post activities to an inbox, belonging to
    *  an actor, held by this server. Also all side effects of the activity are processed.
    * @param  actorname name of the actor whose inbox is the target
    * @param  activity  activity to post and process
    */
    @RequestMapping(value = "/{actorname}/inbox", method = RequestMethod.POST)
    public void postInbox(@PathVariable("actorname") String actorname, @RequestBody Activity activity) {
        
        // post activity to the actors inbox
    	storage.addToInbox(actorname, new LinkOrObject(activity));
        
    	if(activity instanceof Create) {
    		Create createActivity = (Create) activity;
    		LinkOrObject toSave = createActivity.getObject();

    		storage.createObject(toSave.getId(), toSave.getLitObject());
    	}
    	else if(activity instanceof Like) {
			activity.handle(actorname, storage, serverPort);
    	}
        
        // TODO: notify client
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
}
