package de.tuberlin.tkn.lit.model.actors;

import de.tuberlin.tkn.lit.model.Actor;

public class Person extends Actor {
    private static final String type = "Person";

    public Person() {}

    public Person(Actor actor) { super(actor); }

    @Override
    public String getType() {
        return type;
    }
}
