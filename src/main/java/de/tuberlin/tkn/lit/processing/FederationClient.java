package de.tuberlin.tkn.lit.processing;

import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.controller.ServerController;
import de.tuberlin.tkn.lit.constants.UriConstants;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class FederationClient implements IFederationClient{

	@Value("${server.port}")
    private int serverPort;

	@Autowired
	IStorage storage;

	@Autowired
	ServerController serverController;

	private String hostUrl = UriConstants.HOST + serverPort;

	private ExecutorService executor = Executors.newSingleThreadExecutor();
	volatile ResponseEntity<String> result;

	/**
    *  Allows sending Activities to an actor (their inbox) on another server
    * @param  activity the activity to send
    * @param  sendTo actor who's supposed to receive the activity 
	* @return whether the request was successful or not
    */
	@Override
	public Future<Boolean> send(Activity activity, LinkOrObject sendTo) {
		return executor.submit(() -> {
			String url = sendTo.getLink() + "/inbox"; // link is guranteed

			// send post request
			int statusCode = sendWithTimeout(activity, url);

			// check if request was successful
    		if (200 >= statusCode && statusCode < 300) {
				return true;
			}

			// handle post wasn't successful
			url = sendTo.getLink();
			URL urlObj = new URL(url);
			String host = urlObj.getHost() + ':' + urlObj.getPort();
			storage.addPendingActivity(host, activity);

			return false;
		});
	}
		

	/**
    *  (Helper) Send method so we can timeout the request
    * @param  activity the activity to be send
	* @param  url the receiver of the activity
	* @return the status code returned by the request
    */
	public int sendWithTimeout(Activity activity, String url) {
		
		result = new ResponseEntity("Timeout", HttpStatus.BAD_REQUEST);
		RestTemplate restTemplate = new RestTemplate();

		// thread for request execution
		Thread sendThread = new Thread() {
			public void run() {
				try {
					// TODO : does the default have a timeout of it's own (60)
					result = restTemplate.postForEntity(url, activity, String.class);
				} catch(Exception e) {

				}
			}  
		};		
		
		// send request with timeout
		sendThread.start();
		try{
			Thread.sleep(500);
		} catch(InterruptedException e) {}
		sendThread = null;

		return result.getStatusCodeValue();
	}


	/**
    *  Allows getting the outbox of an actor on another server
    * @param  getFrom actor who's outbox shall be retrieved 
	* @return the ordered collection that is the outbox
    */
	@Override
	public Future<OrderedCollection> getOutbox(LinkOrObject getFrom) {
		return executor.submit(() -> {
			String url = getFrom.getLink() + "/outbox";
			
			// send get request
    		RestTemplate restTemplate = new RestTemplate();
			// TODO : we need timeout here aswell
    		ResponseEntity<OrderedCollection> result = restTemplate.getForEntity(url, OrderedCollection.class);
			return result.getBody();
		});
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

		// inform the other servers about the new one aswell
    	for (String host : federationMembers) {
            
            // server shouldn't request activities from itself
            if (this.hostUrl.equals("http://" + host)) {
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
            ResponseEntity<Activity[]> result;
            try {
                RestTemplate restTemplate = new RestTemplate();
    		    result = restTemplate.postForEntity(url, this.hostUrl, Activity[].class);
            }
            catch(HttpClientErrorException e) {
                return new ArrayList<Activity>();
            }

			// check if request was successful
    		if (200 >= result.getStatusCodeValue() && result.getStatusCodeValue() < 300) {
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