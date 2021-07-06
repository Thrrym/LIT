package de.tuberlin.tkn.lit;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.controller.ServerController;

public class FederationJoin {

    @Autowired
	IStorage storage;

	// TODO : get the actual server port
    private int serverPort = 8000; 
    private String hostUrl = UriConstants.HOST + serverPort;

    public FederationJoin() {
    }

	/**
    *  Handles the case this server wants to join a federation
    * @param  knownMember The base url of a known member.
    */
	public void handleJoinFederation(String knownMember) {
		List<Activity> pendingActivities = null;
		List<String> federationMembers = null;

		while(true) {
            try {
                // get pendingActivities
                pendingActivities = joinFederation(knownMember);

				// get other federation members
                federationMembers = getFederationMembers(knownMember);
			}
			catch(Exception e) {}

			// handle successful join
			if (pendingActivities != null || federationMembers != null) {
				System.out.print("Connected to " + knownMember + "\n");
				break;
			}

			System.out.print("Couldn't establish connection with " + knownMember + ", trying again in 2 seconds \n");

			try {
				TimeUnit.SECONDS.sleep(2);;
			}
			catch(Exception e) {}				
		}

        System.out.print("others \n");
		// inform the other servers about the new one aswell
    	for (String host : federationMembers) {
            
            // server shouldn't request activities from itself
            System.out.print("host " + host + " this.hostUrl " + this.hostUrl);
            if (host == this.hostUrl) {
                continue;
            }

			// TODO : if this server holds pending activities for others, send them 
            // (makes sense when there is persistent storage)

			// get the staging activities, ignore their known servers
    	    String url = "http://" + host;
			List<Activity> morePendingActivities = joinFederation(url); // TODO : timeouts ?!
			pendingActivities.addAll(morePendingActivities);
    	}

		// process the pending activities
        System.out.print("process \n");
        ServerController serverController = new ServerController();
		for(Activity activity : pendingActivities) {
			String[] urlParts = activity.getActor().getLink().split("/");
			String actorname = urlParts[urlParts.length-1];
			
			// process
			serverController.handleActivity(actorname, activity);
		}
	} 

	/**
    *  Allows requesting to join a federation.
    * @param  baseUrl The base url of a known member.
	* @return List of activities the member had left for the joining one.
    */
	private List<Activity> joinFederation(String baseUrl) {
			String url = baseUrl + "/join-federation";

            // TODO : get activities meant for the server, if any exist and send them
			// List<Activity> pending = storage.getPendingActivities(baseUrl);

			// send post request
            System.out.print("Send \n");
            ResponseEntity<Activity[]> result;
            try {
                RestTemplate restTemplate = new RestTemplate();
    		    result = restTemplate.postForEntity(url, this.hostUrl, Activity[].class);
            }
            catch(HttpClientErrorException e) {
                return new ArrayList<Activity>();
            }
    		
            System.out.print("Result : " + result + "\n");

			// check if request was successful
    		if (200 >= result.getStatusCodeValue() && result.getStatusCodeValue() < 300) {
				System.out.print("Status : " + result.getStatusCodeValue() + "\n");
                System.out.print("Body : " + result.getBody() + "\n");
                return Arrays.asList(result.getBody());
			}

			return new ArrayList<Activity>();
	}

	/**
    *  Allows to request which hosts are members of the federation.
    * @param  baseUrl The base url of a known member.
	* @return List of members in federation.
    */
	private List<String> getFederationMembers(String url) {
		
		url += "/federation-members";

		// send get request
    	RestTemplate restTemplate = new RestTemplate();
		
    	ResponseEntity<String[]> result = restTemplate.getForEntity(url, String[].class);
		return Arrays.asList(result.getBody());
	}

}
