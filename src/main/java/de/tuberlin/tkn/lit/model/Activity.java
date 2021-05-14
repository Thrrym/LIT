package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(value = { "@context" })
public class Activity extends ActivityPubObject {
    private static final String type = "Activity";

    @JsonProperty("@context")
    private String context;
    private ActivityPubObject actor;
    private ActivityPubObject object;
    private ActivityPubObject target;
    private ActivityPubObject result;
    private ActivityPubObject origin;
    private ActivityPubObject instrument;

    public Activity(String context, ActivityPubObject actor, ActivityPubObject object, ActivityPubObject target, ActivityPubObject result, ActivityPubObject origin, ActivityPubObject instrument) {
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

    public String getType() {
        return type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ActivityPubObject getActor() {
        return actor;
    }

    public void setActor(ActivityPubObject actor) {
        this.actor = actor;
    }

    public ActivityPubObject getObject() {
        return object;
    }

    public void setObject(ActivityPubObject object) {
        this.object = object;
    }

    public ActivityPubObject getTarget() {
        return target;
    }

    public void setTarget(ActivityPubObject target) {
        this.target = target;
    }

    public ActivityPubObject getResult() {
        return result;
    }

    public void setResult(ActivityPubObject result) {
        this.result = result;
    }

    public ActivityPubObject getOrigin() {
        return origin;
    }

    public void setOrigin(ActivityPubObject origin) {
        this.origin = origin;
    }

    public ActivityPubObject getInstrument() {
        return instrument;
    }

    public void setInstrument(ActivityPubObject instrument) {
        this.instrument = instrument;
    }
}
