package de.tuberlin.tkn.lit.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import de.tuberlin.tkn.lit.serializer.ArraySerializer;
import de.tuberlin.tkn.lit.deserializer.ArrayDeserializer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import de.tuberlin.tkn.lit.serializer.ArraySerializer;

import java.util.List;

public class OrderedCollection extends LitCollection {

    private List<LinkOrObject> orderedItems;

    public OrderedCollection() {
        super();
    }
    
    public OrderedCollection(List<LinkOrObject> orderedItems, int totalItems, LinkOrObject first, LinkOrObject last, LinkOrObject current) {
        super(totalItems, first, last, current);
        this.orderedItems = orderedItems;
    }

    public OrderedCollection(List<LinkOrObject> orderedItems) {
        super();
        this.orderedItems = orderedItems;
        this.setType("OrderedCollection");
    }

    @JsonGetter("orderedItems")
    public List<JsonNode> toJSONOrderedItems() throws JsonProcessingException {
        if (orderedItems == null) return null;
        return ArraySerializer.serialize(orderedItems);
    }

    public List<LinkOrObject> getOrderedItems() {
        return orderedItems;
    }

    //serialize
    @JsonGetter("orderedItems")
    public List<String> getJson() throws JsonProcessingException {
        return ArraySerializer.serialize(this.orderedItems);
    }

    // deserialize
    @JsonSetter("orderedItems")
    public void setJson(JsonNode s) throws JsonProcessingException {
        orderedItems = ArrayDeserializer.deserialize(s);
    }
}
