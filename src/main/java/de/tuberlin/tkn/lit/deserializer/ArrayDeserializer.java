package de.tuberlin.tkn.lit.deserializer;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tuberlin.tkn.lit.model.LinkOrObject;
import de.tuberlin.tkn.lit.model.LitObject;

import java.util.ArrayList;
import java.util.List;

public class ArrayDeserializer {
    public static List<LinkOrObject> deserialize(JsonNode s) throws JsonProcessingException {
        System.out.println("Deserialize array: "+s);
        List<LinkOrObject> objOrLink = new ArrayList<LinkOrObject>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (JsonNode sN : s) {
            System.out.println(sN);
            if (sN.isObject()) {
                objOrLink.add(objectMapper.treeToValue(sN, LinkOrObject.class));
            } else {
                LinkOrObject new_objOrLink = new LinkOrObject(s.asText());
                objOrLink.add(new_objOrLink);
            }
        }
        return objOrLink;
    }
}

