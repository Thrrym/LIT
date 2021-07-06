package de.tuberlin.tkn.lit.jsonutilities.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

public class LinkOrObjectDeserializer {
    public static LinkOrObject deserialize(JsonNode s) throws JsonProcessingException {
        if (s.isObject()) {
            ObjectMapper objectMapper = new ObjectMapper();
            ActivityPubObject litObj = objectMapper.treeToValue(s, ActivityPubObject.class);
            return new LinkOrObject(litObj);
        } 
        return new LinkOrObject(s.asText());
    }
}
