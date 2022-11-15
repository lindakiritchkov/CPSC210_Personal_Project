package persistence;
// The following code is adapted from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the JsonWriter class
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterValidFile() {
        try {
            // makes a JSONObject to represent a vegetable
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type","carrot");
            jsonObject.put("daysBetweenWatering", 1);
            jsonObject.put("daysSinceLastWatered", 1);

            JsonWriter writer = new JsonWriter("./data/testWriter.json");
            writer.open();
            writer.write(jsonObject);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriter.json");
            jsonObject = reader.read();
            assertEquals("carrot", jsonObject.getString("type"));
            assertEquals(1,jsonObject.getInt("daysBetweenWatering"));
            assertEquals(1,jsonObject.getInt("daysSinceLastWatered"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
