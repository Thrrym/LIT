package de.tuberlin.tkn.lit.processing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import de.tuberlin.tkn.lit.storage.IStorage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class ServerToServerCommunicator implements IActivitySender{

	@Autowired
	IStorage storage;

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
    		if (200 >= result.getStatusCodeValue() && result.getStatusCodeValue() < 300) {
				return true;
			}

			// TODO : handle post wasn't successful
			System.out.print(sendTo.getLink());
			storage.addPendingActivity(sendTo.getLink(), activity);

			return false;
		});
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
    		ResponseEntity<OrderedCollection> result = restTemplate.getForEntity(url, OrderedCollection.class);
			return result.getBody();
		});
	}
}