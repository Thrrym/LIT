package de.tuberlin.tkn.lit.storage;

import java.util.List;
import java.util.Map;

import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.OrderedCollection;
import de.tuberlin.tkn.lit.model.LinkOrObject;

public interface IStorage {
	Actor createActor(Actor actor);
	boolean removeActor(Actor actor);
	//Map<String, Actor> getActors();
	Actor getActor(String actorName);
	OrderedCollection getInbox(String actorName);
	OrderedCollection getOutbox(String actorName);
	void addInbox(String actorName, LinkOrObject toAdd);
	void addOutbox(String actorName, LinkOrObject toAdd);
}
