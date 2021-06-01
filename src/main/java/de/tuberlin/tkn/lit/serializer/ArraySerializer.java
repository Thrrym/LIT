package de.tuberlin.tkn.lit.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;

import java.util.ArrayList;
import java.util.List;

public class ArraySerializer {
    public static List<String> serialize(List<LinkOrObject> linkOrObjectArray) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        List<String> result = new ArrayList<>();
        for (LinkOrObject linkOrObject : linkOrObjectArray) {
            if (linkOrObject.isObject()) {
                result.add(ow.writeValueAsString(linkOrObject.getLitObject()));
            } else {
                result.add(linkOrObject.getLink());
            }
        }
        return result;
    }
}
