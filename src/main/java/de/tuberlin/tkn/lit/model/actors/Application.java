package de.tuberlin.tkn.lit.model.actors;

import de.tuberlin.tkn.lit.model.Actor;

public class Application extends Actor {
    private static final String type = "Application";

    public Application() {}

    public Application(Actor actor) { super(actor); }

    @Override
    public String getType() {
        return type;
    }
}
