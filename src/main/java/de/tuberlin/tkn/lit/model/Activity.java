package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(value = { "@context" })
public class Activity extends LitObject {
    private static final String type = "Activity";

    @JsonProperty("@context")
    private String context;
    private LitObject actor;
    private LitObject object;
    private LitObject target;
    private LitObject result;
    private LitObject origin;
    private LitObject instrument;

    public Activity() {
    }

    public Activity(String context, LitObject actor, LitObject object, LitObject target, LitObject result, LitObject origin, LitObject instrument) {
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

    public LitObject getActor() {
        return actor;
    }

    public void setActor(LitObject actor) {
        this.actor = actor;
    }

    public LitObject getObject() {
        return object;
    }

    public void setObject(LitObject object) {
        this.object = object;
    }

    public LitObject getTarget() {
        return target;
    }

    public void setTarget(LitObject target) {
        this.target = target;
    }

    public LitObject getResult() {
        return result;
    }

    public void setResult(LitObject result) {
        this.result = result;
    }

    public LitObject getOrigin() {
        return origin;
    }

    public void setOrigin(LitObject origin) {
        this.origin = origin;
    }

    public LitObject getInstrument() {
        return instrument;
    }

    public void setInstrument(LitObject instrument) {
        this.instrument = instrument;
    }
}
