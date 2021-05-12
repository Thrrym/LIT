package de.tuberlin.tkn.lit;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class ParseJsonTest {

    @Test
    void testExampleJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("activity.json");
        Activity activity = objectMapper.readValue(file, Activity.class);
        // assert
    }
}
