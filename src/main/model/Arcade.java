package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Arcade {
    private static final String JSON_STORE = "./data/arcade.json";
    private final List<PlayerProfile> crossyRoadPlayerProfileList;
    private final List<PlayerProfile> memoryPlayerProfileList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //private JsonReader jsonReader;

    /*
     * MODIFIES: this
     * EFFECTS: instantiates a new PlayerProfile ArrayList
     *          instantiates a new Scanner object
     *          calls displayOptions
     */
    public Arcade() {
        this.crossyRoadPlayerProfileList = new ArrayList<>();
        this.memoryPlayerProfileList = new ArrayList<>();
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public void loadArcade() {
        try {
            jsonReader.read(this);
            System.out.println("Loaded Arcade from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void addPlayerProfile(PlayerProfile newProfile, String gameName) {
        switch (gameName) {
            case "CROSSYROAD":
                this.crossyRoadPlayerProfileList.add(newProfile);
                break;
            case "MEMORY":
                this.memoryPlayerProfileList.add(newProfile);
                break;
        }
    }

    public List<PlayerProfile> getPlayerProfileList(String gameName) {
        switch (gameName) {
            case "CROSSYROAD":
                return this.crossyRoadPlayerProfileList;
            case "MEMORY":
                return this.memoryPlayerProfileList;
            default:
                return new ArrayList<>();
        }
    }

    // Taken from JSONSerialization
    // EFFECTS: saves the workroom to file
    public void saveArcade() {
        try {
            jsonWriter.open();
            jsonWriter.write(arcadeToJson());
            jsonWriter.close();
            System.out.println("Saved all arcade data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        System.out.println("Quitting Game...");
        System.exit(0);
    }

    public JSONArray arcadeToJson() {
        JSONArray jsonArcadeArray = new JSONArray();
        for (GameName name : GameName.values()) {
            jsonArcadeArray.put(gameToJson(name));
        }
        return jsonArcadeArray;
    }

    public JSONObject gameToJson(GameName name) {
        JSONObject jsonGameObject = new JSONObject();
        jsonGameObject.put("Profiles", profilesToJson(name));
        jsonGameObject.put("Game Name", name);

        return jsonGameObject;
    }

    private JSONArray profilesToJson(GameName name) {
        JSONArray jsonProfileArray = new JSONArray();
        switch (name) {
            case CROSSYROAD:
                for (PlayerProfile profile : this.crossyRoadPlayerProfileList) {
                    jsonProfileArray.put(profile.profileToJsonObject());
                }
                break;
            case MEMORY:
                for (PlayerProfile profile : this.memoryPlayerProfileList) {
                    jsonProfileArray.put(profile.profileToJsonObject());
                }
        }


        return jsonProfileArray;
    }

    public void setJsonWriter(JsonWriter writer) {
        this.jsonWriter = writer;
    }

    public JsonReader getJsonReader() {
        return this.jsonReader;
    }

}

