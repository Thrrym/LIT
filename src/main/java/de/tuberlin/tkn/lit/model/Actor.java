package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.tuberlin.tkn.lit.deserializer.ActorDeserializer2;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class Actor extends LitObject {

    // TODO:  Attribute and getter/setter https://www.w3.org/TR/activitypub/#actors

    private static final String type = "Actor";

    public String getType() {
        return type;
    }
}
