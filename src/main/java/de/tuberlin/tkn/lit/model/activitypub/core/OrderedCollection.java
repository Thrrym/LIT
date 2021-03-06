package de.tuberlin.tkn.lit.model.activitypub.core;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import de.tuberlin.tkn.lit.jsonutilities.serializer.ArraySerializer;
import de.tuberlin.tkn.lit.jsonutilities.deserializer.ArrayDeserializer;

import java.util.List;

public class OrderedCollection extends ActivityPubCollection {

    private List<LinkOrObject> orderedItems;

    public OrderedCollection() {
        super();
    }
    
    public OrderedCollection(List<LinkOrObject> orderedItems, LinkOrObject first, LinkOrObject last, LinkOrObject current) {
        super(first, last, current);
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

    // deserialize
    @JsonSetter("orderedItems")
    public void setJson(JsonNode s) throws JsonProcessingException {
        orderedItems = ArrayDeserializer.deserialize(s);
    }

    @Override
    @JsonGetter("totalItems")
    public int getTotalItems() {
        if(orderedItems == null)
            return 0;
        return orderedItems.size();
    }

    @Override
    @JsonSetter("totalItems")
    public void setTotalItems(int value){}
}
