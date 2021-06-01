package de.tuberlin.tkn.lit.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;

import java.util.ArrayList;
import java.util.List;

public class ArrayDeserializer {
    public static List<LinkOrObject> deserialize(JsonNode s) throws JsonProcessingException {
        List<LinkOrObject> objOrLink = new ArrayList<LinkOrObject>();
        if (s.isArray()) {
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode sN : s) {
                if (sN.isObject()) {
                    LitObject litObj = objectMapper.treeToValue(sN, LitObject.class);
                    objOrLink.add(new LinkOrObject(litObj));
                } else {
                    objOrLink.add(new LinkOrObject(sN.asText()));
                }
            }
            return objOrLink;
        }
        objOrLink.add(new LinkOrObject(s.asText()));
        return objOrLink;
    }
}

