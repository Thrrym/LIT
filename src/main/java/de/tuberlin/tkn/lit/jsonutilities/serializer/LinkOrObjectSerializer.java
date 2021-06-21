package de.tuberlin.tkn.lit.jsonutilities.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

public class LinkOrObjectSerializer {
    public static JsonNode serialize(LinkOrObject linkOrObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (linkOrObject.isObject()) return mapper.valueToTree(linkOrObject.getLitObject());
        return mapper.valueToTree(linkOrObject.getLink());
    }
}
