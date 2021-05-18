package de.tuberlin.tkn.lit.model.actors;

import de.tuberlin.tkn.lit.model.Actor;

public class Organization extends Actor {
    private static final String type = "Organization";

    public Organization() {}

    public Organization(Actor actor) { super(actor); }

    @Override
    public String getType() {
        return type;
    }
}
