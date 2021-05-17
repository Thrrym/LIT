package de.tuberlin.tkn.lit.model.actors;

import de.tuberlin.tkn.lit.model.Actor;

public class Group extends Actor {
    private static final String type = "Group";

    public Group() {}

    public Group(Actor actor) { super(actor); }

    @Override
    public String getType() {
        return type;
    }
}
