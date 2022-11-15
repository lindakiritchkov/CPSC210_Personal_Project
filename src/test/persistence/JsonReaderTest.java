package persistence;
// The following code is adapted from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests for the JsonReader class

public class JsonReaderTest {

    @Test
    void testReaderInvalidFile() {
        try {
            JsonReader reader = new JsonReader("./data/my/illegalfileName.json");
            reader.read();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderValidFiles() {
        try {
            JsonReader reader = new JsonReader("./data/testReader.json");
            JSONObject jsonObject = reader.read();
            assertEquals("carrot", jsonObject.getString("type"));
            assertEquals(1,jsonObject.getInt("daysBetweenWatering"));
            assertEquals(1,jsonObject.getInt("daysSinceLastWatered"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
