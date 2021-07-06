package de.tuberlin.tkn.lit.model.activitypub.core;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.jsonutilities.deserializer.ArrayDeserializer;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class ActivityPubCollection extends ActivityPubObject {

    @OneToMany(targetEntity = LinkOrObject.class)
    private List<LinkOrObject> items;
    private int totalItems;
    @OneToOne(targetEntity = LinkOrObject.class)
    private LinkOrObject first;
    @OneToOne(targetEntity = LinkOrObject.class)
    private LinkOrObject last;
    @OneToOne(targetEntity = LinkOrObject.class)
    private LinkOrObject current;

    public ActivityPubCollection() {
    }

    public ActivityPubCollection(int totalItems, LinkOrObject first, LinkOrObject last, LinkOrObject current) {
        this.totalItems = totalItems;
        this.first = first;
        this.last = last;
        this.current = current;
    }

    public ActivityPubCollection(List<LinkOrObject> items, int totalItems, LinkOrObject first, LinkOrObject last, LinkOrObject current) {
        this(totalItems, first, last, current);
        this.items = items;
    }

    public List<LinkOrObject> getItems() {
        return items;
    }

    public void setItems(List<LinkOrObject> items) {
        this.items = items;
    }

    @JsonSetter("items")
    public void setItems(JsonNode s) throws JsonProcessingException {
        items = ArrayDeserializer.deserialize(s);
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public LinkOrObject getFirst() {
        return first;
    }

    public void setFirst(LinkOrObject first) {
        this.first = first;
    }

    public LinkOrObject getLast() {
        return last;
    }

    public void setLast(LinkOrObject last) {
        this.last = last;
    }

    public LinkOrObject getCurrent() {
        return current;
    }

    public void setCurrent(LinkOrObject current) {
        this.current = current;
    }
}