package de.tuberlin.tkn.lit.model;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

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

    @Test
    void testExample5CollectionJson() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(classLoader.getResource("example_5_collection.json").getFile());
        LitCollection collection = objectMapper.readValue(file, LitCollection.class);

        System.out.println("Type: "+collection.getType());
        System.out.println("Total items: "+collection.getTotalItems());
        System.out.println("First item type: "+collection.getItems().get(0).getObject().getType());
        System.out.println("Note name: "+collection.getItems().get(0).getObject().getName());

        assertEquals(collection.getType(), "Collection");
        assertEquals(collection.getTotalItems(), 2);
        assertEquals(collection.getItems().get(0).getObject().getType(), "Note");
        assertEquals(collection.getItems().get(0).getObject().getName(), "A Simple Note");

    }
}
