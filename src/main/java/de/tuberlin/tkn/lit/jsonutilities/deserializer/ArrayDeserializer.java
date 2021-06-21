package de.tuberlin.tkn.lit.jsonutilities.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import java.util.ArrayList;
import java.util.List;

public class ArrayDeserializer {
    public static List<LinkOrObject> deserialize(JsonNode s) throws JsonProcessingException {
        List<LinkOrObject> objOrLink = new ArrayList<LinkOrObject>();
        if (s.isArray()) {
            ObjectMapper objectMapper = new ObjectMapper();
            for (JsonNode sN : s) {
                if (sN.isObject()) {
                    ActivityPubObject litObj = objectMapper.treeToValue(sN, ActivityPubObject.class);
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

