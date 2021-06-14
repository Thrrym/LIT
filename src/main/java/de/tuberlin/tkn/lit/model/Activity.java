package de.tuberlin.tkn.lit.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.tkn.lit.deserializer.LinkOrObjectDeserializer;
import de.tuberlin.tkn.lit.processing.IActivitySender;
import de.tuberlin.tkn.lit.serializer.LinkOrObjectSerializer;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

import java.util.List;
import java.util.logging.Logger;

public abstract class Activity extends LitObject {

    @JsonIgnore
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private LinkOrObject actor;
    private LinkOrObject object;
    private LinkOrObject target;
    private LinkOrObject result;
    private LinkOrObject origin;
    private LinkOrObject instrument;

    public Activity() {
    }

    public Activity(LinkOrObject actor, LinkOrObject object, LinkOrObject target, LinkOrObject result, LinkOrObject origin, LinkOrObject instrument) {
        this.actor = actor;
        this.object = object;
        this.target = target;
        this.result = result;
        this.origin = origin;
        this.instrument = instrument;
    }

    public Activity(Activity activity) {
        this.actor = activity.actor;
        this.object = activity.object;
        this.target = activity.target;
        this.result = activity.result;
        this.origin = activity.origin;
        this.instrument = activity.instrument;
    }

    public abstract Activity handle(String actorName, IStorage storage);

    public void handleSendings(IStorage storage, IActivitySender activitySender) {
        handleSendingsIntern(getTo(), storage, activitySender);
        handleSendingsIntern(getCc(), storage, activitySender);
        handleSendingsIntern(getBto(), storage, activitySender);
        handleSendingsIntern(getBcc(), storage, activitySender);
    }

    private void handleSendingsIntern(List<LinkOrObject> list, IStorage storage, IActivitySender activitySender) {
        if (list != null) {
            for (LinkOrObject linkOrObject : list) {
                if (UriUtilities.isLocaleServer(linkOrObject.getLink())) {
                    try {
                        OrderedCollection inbox = storage.getInbox(UriUtilities.getActor(linkOrObject.getLink()));
                        inbox.getOrderedItems().add(new LinkOrObject(this));
                        OrderedCollection relevantObjects = storage.getRelevantObjects(UriUtilities.getActor(linkOrObject.getLink()));
                        relevantObjects.getOrderedItems().add(this.getObject());
                    } catch (NullPointerException ex) {
                        logger.warning("The inbox for the actor '" + linkOrObject.getLink() + "' could not be found.");
                    }
                } else {
                    activitySender.send(this);
                }
            }
        }
    }

    public LinkOrObject getActor() {
        return actor;
    }

    @JsonGetter("actor")
    public JsonNode toJSONActor() throws JsonProcessingException {
        return LinkOrObjectSerializer.serialize(actor);
    }

    public void setActor(LinkOrObject actor) {
        this.actor = actor;
    }

    @JsonSetter("actor")
    public void setActor(JsonNode s) throws JsonProcessingException {
        actor = LinkOrObjectDeserializer.deserialize(s);
    }

    public LinkOrObject getObject() {
        return object;
    }

    /*@JsonGetter("object")
    public LitObject toJSONObject() throws JsonProcessingException {
        LitObject litObject = LinkOrObjectSerializer.serialize(object);
        return litObject;
    }*/

    @JsonGetter("object")
    public JsonNode toJSONObject() throws JsonProcessingException {
        return LinkOrObjectSerializer.serialize(object);
    }

    public void setObject(LinkOrObject object) {
        this.object = object;
    }

    @JsonSetter("object")
    public void setObject(JsonNode s) throws JsonProcessingException {
        object = LinkOrObjectDeserializer.deserialize(s);
    }

    public LinkOrObject getTarget() {
        return target;
    }

    public void setTarget(LinkOrObject target) {
        this.target = target;
    }

    @JsonSetter("target")
    public void setTarget(JsonNode s) throws JsonProcessingException {
        target = LinkOrObjectDeserializer.deserialize(s);
    }

    public LinkOrObject getResult() {
        return result;
    }

    public void setResult(LinkOrObject result) {
        this.result = result;
    }

    @JsonSetter("result")
    public void setResult(JsonNode s) throws JsonProcessingException {
        result = LinkOrObjectDeserializer.deserialize(s);
    }

    public LinkOrObject getOrigin() {
        return origin;
    }

    public void setOrigin(LinkOrObject origin) {
        this.origin = origin;
    }

    @JsonSetter("origin")
    public void setOrigin(JsonNode s) throws JsonProcessingException {
        origin = LinkOrObjectDeserializer.deserialize(s);
    }

    public LinkOrObject getInstrument() {
        return instrument;
    }

    public void setInstrument(LinkOrObject instrument) {
        this.instrument = instrument;
    }

    @JsonSetter("instrument")
    public void setInstrument(JsonNode s) throws JsonProcessingException {
        instrument = LinkOrObjectDeserializer.deserialize(s);
    }
}
