package de.tuberlin.tkn.lit.storage;

import java.util.List;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.OrderedCollection;
import de.tuberlin.tkn.lit.model.LinkOrObject;

public interface IStorage {
	boolean AddActor(Actor actor);
	boolean RemoveActor(Actor actor);
	List<Actor> GetActors();
	OrderedCollection GetInbox(String actorName);
	OrderedCollection GetOutbox(String actorName);
	void AddInbox(String actorName, LinkOrObject toAdd);
	void AddOutbox(String actorName, LinkOrObject toAdd);
}
