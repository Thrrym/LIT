package de.tuberlin.tkn.lit.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.OrderedCollection;

@Service
public class Storage implements IStorage {
    private final Map<Actor, OrderedCollection> outboxes = new HashMap<Actor, OrderedCollection>();
    private final Map<Actor, OrderedCollection> inboxes = new HashMap<Actor, OrderedCollection>();
    private List<Actor> actors = new ArrayList<Actor>();
	
	@Override
	public List<Actor> GetActors() {
		return actors;
	}

	@Override
	public OrderedCollection GetInbox(Actor actor) {
		return inboxes.get(actor);
	}

	@Override
	public OrderedCollection GetOutbox(Actor actor) {
		return outboxes.get(actor);
	}
}