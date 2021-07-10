package de.tuberlin.tkn.lit;

import de.tuberlin.tkn.lit.processing.IFederationClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class LitApplication {
	
	@Autowired
	IFederationClient federationClient;

	private static String[] argsStore = {};

	public static void main(String[] args) {
		argsStore = args;
		SpringApplication.run(LitApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void joinFederation() {
		
		// get command line arguments
		// expecting the optional known 
		// federation member to be first
		String knownMember = argsStore.length >= 1 ? argsStore[0] : "";

		// the server wants to join a federation and
		// supplies the adress of a member
		if (!knownMember.isEmpty()) {
			System.out.print("Try joining a federation \n");
			federationClient.handleJoinFederation(knownMember);
		}
	}
}
