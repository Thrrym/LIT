package de.tuberlin.tkn.lit.storage;

import java.util.List;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.OrderedCollection;

public interface IStorage {
	boolean AddActor(Actor actor);
	boolean RemoveActor(Actor actor);
	List<Actor> GetActors();
	OrderedCollection GetInbox(Actor actor);
	OrderedCollection GetOutbox(Actor actor);
}
