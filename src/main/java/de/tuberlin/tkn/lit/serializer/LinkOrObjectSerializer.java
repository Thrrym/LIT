package de.tuberlin.tkn.lit.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.tuberlin.tkn.lit.model.LinkOrObject;

public class LinkOrObjectSerializer {
    public static String serialize(LinkOrObject linkOrObject) throws JsonProcessingException {
        if (linkOrObject.isObject()) {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(linkOrObject.getLitObject());
            return json;
        }
        return linkOrObject.getLink();
    }
}
