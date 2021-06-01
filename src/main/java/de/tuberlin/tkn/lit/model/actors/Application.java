package de.tuberlin.tkn.lit.model.actors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.model.Actor;

public class Application extends Actor {

    @JsonIgnore
    private static final String TYPE_NAME = "Application";

    public Application() {}

    public Application(Actor actor) { super(actor); }
}
