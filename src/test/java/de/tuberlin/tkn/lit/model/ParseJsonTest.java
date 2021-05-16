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

        ObjectMapper objectMapper = new ObjectMapper();;//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /*SimpleModule module = new SimpleModule();
        module.addDeserializer(Actor.class, new ActorDeserializer2());
        objectMapper.registerModule(module);*/
        //File file = new File(classLoader.getResource("activity.json").getFile());
        //File file = new File(classLoader.getResource("create.json").getFile());
        File file = new File(classLoader.getResource("create_2.json").getFile());

        Activity activity = objectMapper.readValue(file, Activity.class);
        //assertEquals(activity.getObject().getContent(), "This is a note");
        System.out.println("Activity Type: "+activity.getType());
        //System.out.println("Actor type: "+activity.getActor().getLink());
        System.out.println("Object class: "+activity.getObject().getObject().getClass());
        //MatcherAssert.assertThat(activity.getObject(), instanceOf(Note.class));
        //assertEquals(activity.getActor().getName(), "Sally");
        //MatcherAssert.assertThat(object.getActor(), instanceOf(Person.class));
    }
}
