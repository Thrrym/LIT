package de.tuberlin.tkn.lit.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;

public class LinkOrObjectDeserializer {
    public static LinkOrObject deserialize(JsonNode s) throws JsonProcessingException {
        if (s.isObject()) {
            ObjectMapper objectMapper = new ObjectMapper();
            LitObject litObj = objectMapper.treeToValue(s, LitObject.class);
            return new LinkOrObject(litObj);
        } 
        return new LinkOrObject(s.asText());
    }
}
