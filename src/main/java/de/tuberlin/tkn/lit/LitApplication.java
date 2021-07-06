package de.tuberlin.tkn.lit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.tuberlin.tkn.lit.FederationJoin;

@SpringBootApplication
public class LitApplication {

	public static void main(String[] args) {

		// start server
		SpringApplication.run(LitApplication.class, args);
		
		// get command line arguments
		String knownMember = args.length == 1 ? args[0] : "";

		// the server wants to join a federation and
		// supplies the adress of a member
		if (!knownMember.isEmpty()) {
			System.out.print("Try joining a federation \n");
			FederationJoin federationJoin = new FederationJoin();
			federationJoin.handleJoinFederation(knownMember);
		}
	}

}
