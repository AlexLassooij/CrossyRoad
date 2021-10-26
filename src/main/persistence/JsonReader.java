package persistence;

import model.Arcade;
import model.PlayerProfile;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;
    private Arcade arcade;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
        this.arcade = new Arcade();
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Arcade read() throws IOException {
        String jsonData = readFile(source);
        JSONArray arcadeArray = new JSONArray(jsonData);
        parseArcade(arcadeArray);
        return this.arcade;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private void parseArcade(JSONArray arcadeArray) {
        for (int i = 0; i < arcadeArray.length(); i++) {
            JSONObject game = arcadeArray.getJSONObject(i);
            String gameName = game.getString("Game Name");
            JSONArray profiles = game.getJSONArray("Profiles");
            parseProfiles(profiles, gameName);
        }
    }

    // EFFECTS: parses workroom from JSON object and returns it
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
