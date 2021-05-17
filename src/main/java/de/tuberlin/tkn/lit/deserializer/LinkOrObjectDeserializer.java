package de.tuberlin.tkn.lit.deserializer;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.tkn.lit.model.Actor;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;

public class LinkOrObjectDeserializer {
    public static LinkOrObject deserialize(JsonNode s) throws JsonProcessingException {
        if (s.isObject()) {
            ObjectMapper objectMapper = new ObjectMapper();
            LitObject obj = objectMapper.treeToValue(s, LitObject.class);
            return new LinkOrObject(obj);
        } else {
            return new LinkOrObject(s.asText());
        }
    }
}
