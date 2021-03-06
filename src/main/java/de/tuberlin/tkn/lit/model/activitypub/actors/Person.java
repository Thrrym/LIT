package de.tuberlin.tkn.lit.model.activitypub.actors;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Person extends Actor {

    @JsonIgnore
    private static final String TYPE_NAME = "Person";

    public Person() {
    }

    public Person(String name) {
        super(name);
    }

    public Person(String name,String password) {
        super(name,password);
    }

    public Person(Actor actor) {
        super(actor);
    }
}
