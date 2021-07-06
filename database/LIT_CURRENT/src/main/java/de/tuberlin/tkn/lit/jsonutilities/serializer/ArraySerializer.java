package de.tuberlin.tkn.lit.jsonutilities.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;

import java.util.ArrayList;
import java.util.List;

public class ArraySerializer {
    public static List<JsonNode> serialize(List<LinkOrObject> linkOrObjectArray) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        List<JsonNode> result = new ArrayList<>();
        for (LinkOrObject linkOrObject : linkOrObjectArray) {
            result.add(LinkOrObjectSerializer.serialize(linkOrObject));
        }
        return result;
    }
}
