package de.tuberlin.tkn.lit.model.activitypub.activities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.jsonutilities.deserializer.LinkOrObjectDeserializer;
import de.tuberlin.tkn.lit.jsonutilities.serializer.LinkOrObjectSerializer;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.processing.IFederationClient;
import de.tuberlin.tkn.lit.storage.IStorage;
import de.tuberlin.tkn.lit.util.UriUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class Activity extends ActivityPubObject {

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

    public abstract Activity handle(String actorName, IStorage storage, int port);

    public void handleSendings(IStorage storage, IFederationClient federationClient, int port) {
        if (this instanceof Offer) {
            ActivityPubObject objectToUpdate = storage.getObject(getObject().getLitObject().getId());
            handleSendingsIntern(new ArrayList<>() {{
                add(objectToUpdate.getGenerator());
            }}, storage, federationClient, port);
        }

        if (this instanceof Reject || this instanceof Accept) {
            Activity activityToRespond = storage.getActivity(getObject().getId());
            handleSendingsIntern(new ArrayList<>() {{
                add(activityToRespond.getActor());
            }}, storage, federationClient, port);
            return;
        }

        handleSendingsIntern(getTo(), storage, federationClient, port);
        handleSendingsIntern(getCc(), storage, federationClient, port);
        handleSendingsIntern(getBto(), storage, federationClient, port);
        handleSendingsIntern(getBcc(), storage, federationClient, port);
        handleSendingsIntern(storage.getFollowersCollection(UriUtilities.getActor(getActor().getId())).getOrderedItems(), storage, federationClient, port);
    }

    private void handleSendingsIntern(List<LinkOrObject> list, IStorage storage, IFederationClient federationClient, int port) {
        if (list != null) {
            for (LinkOrObject linkOrObject : list) {
                if (UriUtilities.isLocaleServer(linkOrObject.getLink(), port)) {
                    try {
                        storage.addToInbox(UriUtilities.getActor(linkOrObject.getLink()), new LinkOrObject(this));
                        if (this instanceof Create) {
                            storage.addToRelevantObjects(UriUtilities.getActor(linkOrObject.getLink()), getObject());
                        }
                    } catch (NullPointerException ex) {
                        logger.warning("The inbox for the actor '" + linkOrObject.getLink() + "' could not be found.");
                    }
                } else {
                    federationClient.send(this, linkOrObject);
                }
            }
        }
    }

    public LinkOrObject getActor() {
        return actor;
    }

    public void setActor(LinkOrObject actor) {
        this.actor = actor;
    }

    @JsonSetter("actor")
    public void setActor(JsonNode s) throws JsonProcessingException {
        actor = LinkOrObjectDeserializer.deserialize(s);
    }

    @JsonGetter("actor")
    public JsonNode toJSONActor() throws JsonProcessingException {
        return LinkOrObjectSerializer.serialize(actor);
    }

    public LinkOrObject getObject() {
        return object;
    }

    public void setObject(LinkOrObject object) {
        this.object = object;
    }

    @JsonSetter("object")
    public void setObject(JsonNode s) throws JsonProcessingException {
        object = LinkOrObjectDeserializer.deserialize(s);
    }

    @JsonGetter("object")
    public JsonNode toJSONObject() throws JsonProcessingException {
        return LinkOrObjectSerializer.serialize(object);
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
