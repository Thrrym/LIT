package de.tuberlin.tkn.lit.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.tuberlin.tkn.lit.model.objects.Note;


class ParseJsonTest {
//  "@context": "https://www.w3.org/ns/activitystreams",
        /*  "actor": {
        "type": "Person",
                "name": "Sally"
    }, */
    @Test
    void testExampleJson() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        // Ignore @context
        ObjectMapper objectMapper = new ObjectMapper();//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = new File(classLoader.getResource("activity.json").getFile());

        Activity activity = objectMapper.readValue(file, Activity.class);
        assertEquals(activity.getObject().getName(), "A Note");
        MatcherAssert.assertThat(activity.getObject(), instanceOf(Note.class));
    }
}
