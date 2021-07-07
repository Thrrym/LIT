package de.tuberlin.tkn.lit.processing;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.storage.IStorage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

@Service
public class ServerToServerCommunicator implements IActivitySender{

	@Autowired
	IStorage storage;

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

}