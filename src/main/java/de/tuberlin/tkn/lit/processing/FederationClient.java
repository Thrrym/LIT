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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

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

	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private String protocol = "http://";
	
	// Reserved for communication with timeout thread :
	volatile ResponseEntity<String> result;
	volatile Boolean threadDone; 
	
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
			String res = sendWithTimeout(url, activity, String.class, "POST");

			// check if request was successful
    		if (res != null) {
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
    *  (Helper) Send method so we can timeout the requests
    * @param  url The url to request.
	* @param  body The body (null if none) of the request
	* @param  returnType The expected return type.
	* @param  requestType The Type of the http request (currently supports "GET", "POST").
	* @return The returned data of the expected type. Is null if something went wrong.
    */
	public <T> T sendWithTimeout(String url, Object body, Class<T> returnType, String requestType) {
		
		result = new ResponseEntity("Timeout", HttpStatus.BAD_REQUEST);
		threadDone = false;
		RestTemplate restTemplate = new RestTemplate();

		// thread for request execution
		Thread sendThread = new Thread() {
			public void run() {
				try {
					switch(requestType) {
						case "GET":
							result = restTemplate.getForEntity(url, String.class);
							break;

						case "POST":
							result = restTemplate.postForEntity(url, body, String.class);
							break;
					}
				} catch(Exception e) {}
				threadDone = true;
			}  
		};		
		
		// send request with timeout
		sendThread.start();
		int i = 20; // 20 * 500 = 10000 is the timeout
		while(!threadDone && i > 0) {
			try {
				Thread.sleep(500);
			}
			catch(InterruptedException e) { /* TODO : handle this case */}
			i--;
		}
		sendThread = null;

		if (result.getStatusCodeValue() < 200 || result.getStatusCodeValue() >= 300) {
			return null;
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			T res = mapper.readValue(result.getBody(), returnType);
			return res;
		} 
		catch (JsonProcessingException e) {
			return null;
		}
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
    		OrderedCollection result = sendWithTimeout(url, null, OrderedCollection.class, "GET");
			return result;
		});
	}

	/**
    *  Handles the case this server wants to join a federation
    * @param  knownMember The base url of a known member.
    */
	public void handleJoinFederation(String knownMember) {
		List<Activity> pendingActivities = new ArrayList<>();
		ArrayList<String> federationMembers = new ArrayList<>();

		// retry every 2 seconds to get federation
		// information from known member
		while(true) {
			try {
				// get other federation members
				federationMembers = getFederationMembers(this.protocol + knownMember);
				federationMembers.add(knownMember);
				break;
			}
			catch(Exception e) {}

			System.out.print("Couldn't establish connection with " + knownMember + ", trying again in 2 seconds \n");

			try {
				TimeUnit.SECONDS.sleep(2);;
			}
			catch(Exception e) {}				
		}

		System.out.print("Get activities from other hosts in federation : " + federationMembers + "\n");
		// inform the other servers about the new one aswell
    	for (String host : federationMembers) {
            String thisHost = UriConstants.HOST + serverPort;

            // server shouldn't request activities from itself
            if (thisHost.equals(this.protocol + host)) {
				continue;
            }

			// get the staging activities, ignore their known servers
    	    String url = this.protocol + host;
			List<Activity> morePendingActivities = joinFederation(url);
			pendingActivities.addAll(morePendingActivities);
			
			// as a side effect this stores the member
			storage.getPendingActivities(host);
    	}

		// process the pending activities
		for(Activity activity : pendingActivities) {
			String actor = activity.getTo().get(0).getLink();
			String[] urlParts = actor.split("/");
			String actorname = urlParts[urlParts.length-1];
			
			// process
			System.out.print("Handle activity for actor " + actorname + "\n");
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
    		Activity[] result = sendWithTimeout(url, UriConstants.NAME + serverPort, Activity[].class, "POST");

			// check if request was successful
    		if (result != null) {
                return Arrays.asList(result);
			}

			return new ArrayList<Activity>();
	}

	/**
    *  Allows to request which hosts are members of the federation.
    * @param  url The base url of a known member.
	* @return List of members in federation.
    */
	private ArrayList<String> getFederationMembers(String url) {
		url += "/federation-members";

		// send get request		
    	String[] result = sendWithTimeout(url, null, String[].class, "GET");
		return new ArrayList<>(Arrays.asList(result));
	}

}