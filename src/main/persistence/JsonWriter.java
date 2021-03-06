package persistence;

import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Note : a large portion of my JSON IO code has been taken from the JSONSerialization sample project
// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destinationFile;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destinationFile);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(JSONArray arcade) {
        saveToFile(arcade.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
