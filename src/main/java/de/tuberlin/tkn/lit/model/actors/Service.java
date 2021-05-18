package de.tuberlin.tkn.lit.model.actors;

import de.tuberlin.tkn.lit.model.Actor;

public class Service extends Actor {
    private static final String type = "Service";

    public Service() {}

    public Service(Actor actor) { super(actor); }

    @Override
    public String getType() {
        return type;
    }
}
