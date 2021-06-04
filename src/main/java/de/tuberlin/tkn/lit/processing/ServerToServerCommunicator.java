package de.tuberlin.tkn.lit.processing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import de.tuberlin.tkn.lit.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Service;

@Service
public class ServerToServerCommunicator implements IActivitySender{
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
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
    		RestTemplate restTemplate = new RestTemplate();
    		ResponseEntity<String> result = restTemplate.postForEntity(url, activity, String.class);

			// check if request was successful
    		if (201 == result.getStatusCodeValue()) {
				return true;
			}

			return false;
		});
	}

	/**
    *  Allows getting the outbox of an actor on another server
    * @param  getFrom actor who's outbox shall be retrieved 
	* @return the ordered collection that is the outbox
    */
	@Override
	public OrderedCollection getOutbox(LinkOrObject getFrom) {
		String url = getFrom.getLink() + "/outbox";
		
		// send get request
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<OrderedCollection> result = restTemplate.getForEntity(url, OrderedCollection.class);
		
		return result.getBody();
	}
}