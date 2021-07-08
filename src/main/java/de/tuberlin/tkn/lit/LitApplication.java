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

	public static void main(String[] args) {
		SpringApplication.run(LitApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void joinFederation() {
		
		String[] args = { "http://localhost:8080" }; // TODO : this should be from the actual arguments

		// get command line arguments
		String knownMember = args.length == 1 ? args[0] : "";

		// the server wants to join a federation and
		// supplies the adress of a member
		if (!knownMember.isEmpty()) {
			System.out.print("Try joining a federation \n");
			federationClient.handleJoinFederation(knownMember);
		}
	}
}
