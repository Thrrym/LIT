package de.tuberlin.tkn.lit.model;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.tuberlin.tkn.lit.model.objects.Note;

class ParseJsonTest {

    @Test
    void testExample3Json() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        ObjectMapper objectMapper = new ObjectMapper();//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File file = new File(classLoader.getResource("example_3_create_note.json").getFile());
        Activity activity = objectMapper.readValue(file, Activity.class);

        System.out.println("Activity Type: "+activity.getActor().getLink());
        System.out.println("Actor: "+activity.getTo().get(0).getLink());
        System.out.println("Object type: "+activity.getObject().getObject().getType());
        System.out.println("Object content: "+activity.getObject().getObject().getContent());

        assertEquals(activity.getType(), "Create");
        assertEquals(activity.getActor().getLink(), "https://social.example/alyssa/");
        assertEquals(activity.getObject().getObject().getType(), "Note");
        assertEquals(activity.getObject().getObject().getContent(), "Say, did you finish reading that book I lent you?");
    }

    @Test
    void testExample5Json() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(classLoader.getResource("example_5_like.json").getFile());
        Activity activity = objectMapper.readValue(file, Activity.class);

        System.out.println("Activity Type: "+activity.getActor().getLink());
        System.out.println("Actor: "+activity.getTo().get(0).getLink());
        System.out.println("To: "+activity.getTo().get(0).getLink());

        assertEquals(activity.getType(), "Like");
        assertEquals(activity.getActor().getLink(), "https://social.example/alyssa/");
        assertEquals(activity.getTo().get(0).getLink(), "https://chatty.example/ben/");
    }
}
