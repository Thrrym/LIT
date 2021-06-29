package de.tuberlin.tkn.lit;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;

@SpringBootApplication
public class LitApplication {

	public static void main(String[] args) {
		SpringApplication.run(LitApplication.class, args);
		
		String knownMember = "http://localhost:8080";

		// the server wants to join a federation and
		// supplies the adress of a member
		if (!knownMember.isEmpty()) {
			while(true) {
				Boolean res = false;
				
				// try to join he federation
				try {
					res = joinFederation(knownMember);
				}
				catch(Exception e) {}
				
				// handle successful join
				if (res) {
					// TODO : process pending activities
					break;
				}

				System.out.print("Couldn't establish connection with " + knownMember + ", trying again in 2 seconds \n");
				
				try {
					TimeUnit.SECONDS.sleep(2);;
				}
				catch(Exception e) {}				
			}
		}
	}

	/**
    *  Allows requesting to join a federation
    * @param  baseUrl the base url of a known member
	* @return whether the join was successful
    */
	private static Boolean joinFederation(String baseUrl) {
			String url = baseUrl + "/federation";

			// send post request
			System.out.print("Attempt to join : " + url + "\n");
    		RestTemplate restTemplate = new RestTemplate();
    		ResponseEntity<OrderedCollection> result = restTemplate.postForEntity(url, "myUrl", OrderedCollection.class);

			// check if request was successful
    		if (200 >= result.getStatusCodeValue() && result.getStatusCodeValue() < 300) {
				return true;
			}

			return false;
	}

}
