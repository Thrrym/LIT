package de.tuberlin.tkn.lit;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.constants.UriConstants;

@SpringBootApplication
public class LitApplication {

	@Value("${server.port}")
    private String serverPort;

    private static String serverPort_static;

    @Value("${server.port}")
    public void setNameStatic(String serverPort){
        LitApplication.serverPort_static = serverPort;
    }

	public static void main(String[] args) {

		// start server
		SpringApplication.run(LitApplication.class, args);
		
		// get command line arguments
		String knownMember = args.length == 1 ? args[0] : "";

		// the server wants to join a federation and
		// supplies the adress of a member
		if (!knownMember.isEmpty()) {
			System.out.print("Try joining a federation \n");
			handleJoinFederation(knownMember);
		}
	}

	/**
    *  Handles the case this server wants to join a federation
    * @param  knownMember The base url of a known member.
    */
	private static void handleJoinFederation(String knownMember) {
		List<Activity> pendingActivities = null;
		List<String> federationMembers = null;

		while(true) {

			try {
				// get pendingActivities
				// TODO : send pending activities if got any 
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

		// inform the other servers about the new one aswell
    	for (String host : federationMembers) {

			// get the staging activities, ignore their known servers
			// TODO : if this server holds pending activities for others, send them 
    	    String url = "http://" + host;
 			System.out.print("Get pending activities from " + url + "\n");
			List<Activity> morePendingActivities = joinFederation(url); // TODO : timeouts ?!
			pendingActivities.addAll(morePendingActivities);
    	}

		// process the pending activities
		for(Activity activity : pendingActivities) {
			String[] urlParts = activity.getActor().getLink().split("/");
			String actorname = urlParts[urlParts.length-1];
			
			// process
			handleActivity(actorname, activity);
		}
	} 

	/**
    *  Allows requesting to join a federation.
    * @param  baseUrl The base url of a known member.
	* @return List of activities the member had left for the joining one.
    */
	private static List<Activity> joinFederation(String baseUrl) {
			String url = baseUrl + "/join-federation";
			String hostUrl = UriConstants.HOST + serverPort_static;

			// send post request
    		RestTemplate restTemplate = new RestTemplate();
    		ResponseEntity<Activity[]> result = restTemplate.postForEntity(url, hostUrl, Activity[].class);
			
			// check if request was successful
    		if (200 >= result.getStatusCodeValue() && result.getStatusCodeValue() < 300) {
				return Arrays.asList(result.getBody());
			}

			return null;
	}

	/**
    *  Allows to request which hosts are members of the federation.
    * @param  baseUrl The base url of a known member.
	* @return List of members in federation.
    */
	private static List<String> getFederationMembers(String url) {
		
		url += "/federation-members";

		// send get request
    	RestTemplate restTemplate = new RestTemplate();
		
    	ResponseEntity<String[]> result = restTemplate.getForEntity(url, String[].class);
		return Arrays.asList(result.getBody());
	}

	/**
    *  (Helper) Processing an activity for an actors inbox 
	*  (same as post inbox route)
    * @param  actorname
    * @param  activity
    */
    private static void handleActivity(String actorname, Activity activity) {
        // TODO : unite this for server controller and here
    }
}
