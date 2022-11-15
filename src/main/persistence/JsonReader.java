package persistence;
// The following code is adapted from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads garden from JSON data stored in file.
// Data includes the garden (with planted vegetables) and the created vegetables.
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads JSONObject from file and returns it;
    // throws IOException if an error occurs reading data from the file
    public JSONObject read() throws IOException {
        String jsonData = readFile(source);
        return new JSONObject((jsonData));
    }

    // EFFECTS: reads source files as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

}
