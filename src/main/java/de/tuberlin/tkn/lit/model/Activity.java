package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity extends ActivityPubObject {

    private String context;
    private ActivityPubObject actor;
    private ActivityPubObject object;
    private ActivityPubObject target;
    private ActivityPubObject result;
    private ActivityPubObject origin;
    private ActivityPubObject instrument;

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
