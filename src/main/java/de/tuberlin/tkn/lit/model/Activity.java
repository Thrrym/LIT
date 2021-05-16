package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.tuberlin.tkn.lit.deserializer.ActorDeserializer;
import de.tuberlin.tkn.lit.deserializer.ActorDeserializer2;
import de.tuberlin.tkn.lit.model.objects.Link;


public abstract class Activity extends LitObject {
    @JsonProperty("@context")
    private String context;
    //@JsonDeserialize(using = ActorDeserializer2.class)
    private LinkOrObject actor;
    private LinkOrObject object;
    private LinkOrObject target;
    private LinkOrObject result;
    private LinkOrObject origin;
    private LinkOrObject instrument;

    public Activity() {
    }

    public Activity(String context, LinkOrObject actor, LinkOrObject object, LinkOrObject target, LinkOrObject result, LinkOrObject origin, LinkOrObject instrument) {
        this.context = context;
        this.actor = actor;
        this.object = object;
        this.target = target;
        this.result = result;
        this.origin = origin;
        this.instrument = instrument;
    }
    public Activity(Activity activity) {
        this.context = activity.context;
        this.actor = activity.actor;
        this.object = activity.object;
        this.target = activity.target;
        this.result = activity.result;
        this.origin = activity.origin;
        this.instrument = activity.instrument;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LinkOrObject getActor() {
        return actor;
    }

    public void setActor(LinkOrObject actor) {
        this.actor = actor;
    }

    @JsonSetter("actor")
    public void setActor(JsonNode s) throws JsonProcessingException {
        actor = ActorDeserializer.deserialize(s);
    }

    public LinkOrObject getObject() {
        return object;
    }

    public void setObject(LinkOrObject object) {
        this.object = object;
    }

    public LinkOrObject getTarget() {
        return target;
    }

    public void setTarget(LinkOrObject target) {
        this.target = target;
    }

    public LinkOrObject getResult() {
        return result;
    }

    public void setResult(LinkOrObject result) {
        this.result = result;
    }

    public LinkOrObject getOrigin() {
        return origin;
    }

    public void setOrigin(LinkOrObject origin) {
        this.origin = origin;
    }

    public LinkOrObject getInstrument() {
        return instrument;
    }

    public void setInstrument(LinkOrObject instrument) {
        this.instrument = instrument;
    }
}
