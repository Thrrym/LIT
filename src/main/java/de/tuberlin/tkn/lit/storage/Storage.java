package de.tuberlin.tkn.lit.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.OrderedCollection;
import de.tuberlin.tkn.lit.model.LinkOrObject;

@Service
public class Storage implements IStorage {
    private final Map<String, OrderedCollection> outboxes = new HashMap<String, OrderedCollection>();
    private final Map<String, OrderedCollection> inboxes = new HashMap<String, OrderedCollection>();
    private List<Actor> actors = new ArrayList<Actor>();
	
	@Override
	public List<Actor> GetActors() {
		return actors;
	}

	@Override
	public OrderedCollection GetInbox(String actorName) {
		return inboxes.get(actorName);
	}

	@Override
	public OrderedCollection GetOutbox(String actorName) {
		return outboxes.get(actorName);
	}

	@Override
	public void AddInbox(String actorName, LinkOrObject toAdd) {
        inboxes.get(actorName).getOrderedItems().add(toAdd);
	}

	@Override
	public void AddOutbox(String actorName, LinkOrObject toAdd) {
		outboxes.get(actorName).getOrderedItems().add(toAdd);
	}

	@Override
	public boolean AddActor(Actor actor) {
		if(actors.contains(actor))
			return false;
		actors.add(actor);
		outboxes.putIfAbsent(actor.getName(), new OrderedCollection(new ArrayList<>()));
		inboxes.putIfAbsent(actor.getName(), new OrderedCollection(new ArrayList<>()));
		return true;
	}

	@Override
	public boolean RemoveActor(Actor actor) {
		if(!actors.contains(actor))
			return false;
		actors.remove(actor);
		outboxes.remove(actor.getName());
		inboxes.remove(actor.getName());
		return true;
	}
}