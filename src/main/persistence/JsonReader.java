package persistence;

import model.Arcade;
import model.PlayerProfile;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
// Note : a large portion of my JSON IO code has been taken from the JSONSerialization sample project
// Represents a reader that reads arcade from JSON data stored in file

public class JsonReader {
    private final String source;
    private Arcade arcade;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public void read(Arcade arcade) throws IOException {
        this.arcade = arcade;
        String jsonData = readFile(source);
        JSONArray arcadeArray = new JSONArray(jsonData);
        parseArcade(arcadeArray);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // the arcade is stored in a 2D JSON array
    // first reads the elements of the first dimension, that being
    // each game itself
    // EFFECTS: parses arcade from JSON object and returns it
    private void parseArcade(JSONArray arcadeArray) {
        for (int i = 0; i < arcadeArray.length(); i++) {
            JSONObject game = arcadeArray.getJSONObject(i);
            String gameName = game.getString("Game Name");
            JSONArray profiles = game.getJSONArray("Profiles");
            parseProfiles(profiles, gameName);
        }
    }

    // reads the second dimension of the array, that being the information
    // of each player that belongs to the game
    // EFFECTS: parses arcade from JSON object and returns it
    private void parseProfiles(JSONArray profiles, String gameName) {
        for (int i = 0; i < profiles.length(); i++) {
            JSONObject profile = profiles.getJSONObject(i);
            String playerName = profile.getString("playerName");
            int pointsAchieved = profile.getInt("pointsAchieved");
            int levelAchieved = profile.getInt("levelAchieved");
            PlayerProfile playerProfile = new PlayerProfile(playerName, pointsAchieved, levelAchieved);
            this.arcade.addPlayerProfile(playerProfile, gameName);
        }
    }
}
