package de.tuberlin.tkn.lit.model;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParseJsonTest {

    @Test
    void testExample3Json() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        ObjectMapper objectMapper = new ObjectMapper();//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File file = new File(classLoader.getResource("example_3_create_note.json").getFile());
        Activity activity = objectMapper.readValue(file, Activity.class);

        System.out.println("Activity Type: "+activity.getActor().getLink());
        System.out.println("Actor: "+activity.getTo().get(0).getLink());
        System.out.println("Object type: "+activity.getObject().getLitObject().getType());
        System.out.println("Object content: "+activity.getObject().getLitObject().getContent());

        assertEquals(activity.getType(), "Create");
        assertEquals(activity.getActor().getLink(), "https://social.example/alyssa/");
        assertEquals(activity.getObject().getLitObject().getType(), "Note");
        assertEquals(activity.getObject().getLitObject().getContent(), "Say, did you finish reading that book I lent you?");
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
        System.out.println("First item type: "+collection.getItems().get(0).getLitObject().getType());
        System.out.println("Note name: "+collection.getItems().get(0).getLitObject().getName());

        assertEquals(collection.getType(), "Collection");
        assertEquals(collection.getTotalItems(), 2);
        assertEquals(collection.getItems().get(0).getLitObject().getType(), "Note");
        assertEquals(collection.getItems().get(0).getLitObject().getName(), "A Simple Note");

    }

    @Test
    void testExample3JavaToJson() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        //objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        File file = new File(classLoader.getResource("example_3_create_note.json").getFile());
        Activity activity = objectMapper.readValue(file, Activity.class);
        String json = objectMapper.writeValueAsString(activity);
        System.out.println(json);
        //String abc = JSONValue.escape(json);
        //System.out.println(abc);
       /* JSONParser parser = new JSONParser();
        JSONObject jO = (JSONObject) parser.parse(json);
        System.out.println(jO);*/
    }
}
