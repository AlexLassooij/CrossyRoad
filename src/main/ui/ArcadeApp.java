package ui;

import exceptions.QuitGameException;
import model.GameName;
import model.PlayerProfile;
import model.Arcade;
import org.json.JSONArray;
import org.json.JSONObject;
//import persistence.JsonReader;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ArcadeApp {
    private static final String JSON_STORE = "./data/arcade.json";
    private Scanner scanner;
    private JsonReader jsonReader;
    private Arcade arcade;
    //private JsonReader jsonReader;

    /*
     * MODIFIES: this
     * EFFECTS: instantiates a new PlayerProfile ArrayList
     *          instantiates a new Scanner object
     *          calls displayOptions
     */
    public ArcadeApp() {
        this.scanner = new Scanner(System.in);
        this.scanner.useDelimiter("\n");
        jsonReader = new JsonReader(JSON_STORE);
        loadArcade();
        displayOptions();
    }

    /*
     * EFFECTS: displays menu options
     *          calls handleInput to handle the user's choice
     */
    private void displayOptions() {
        System.out.println("\nSelect from options below and press enter");
        System.out.println("\t(1) Create new profile");
        System.out.println("\t(2) Play a game");
        System.out.println("\t(3) Exit arcade");

        handleInput();
    }

    /*
     * EFFECTS: calls createNewProfile is choice is "1"
     *          Asks for further input to determine which player is playing
     *          if choice is "2"
     *          Once player is chosen, startCrossyRoad is called
     */
    private void handleInput() {
        String choice = scanner.next();
        switch (choice) {
            case "1":
                createNewProfile();
                break;
            case "2":
                playGame();
                break;
            case "3":
                saveArcade();
                break;
            default:
                System.out.println("Invalid options");
                displayOptions();
                break;
        }
    }

    private void playGame() {
        System.out.println("What would you like to play");
        int i = 1;
        for (GameName name : GameName.values()) {
            System.out.println("(" + i + ")" + " " + name);
        }
        String choice = scanner.next();

        switch (choice) {
            case "1":
                playCrossyRoad();
                break;
            case "2":
                System.out.println("Playing memory...");
                break;
            default:
                System.out.println("I eat ass");
        }
    }

    private void playCrossyRoad() {
        System.out.println("Who is playing ?");
        int label = 1;
        List<PlayerProfile> playerList = this.arcade.getPlayerProfileList("CROSSYROAD");
        for (PlayerProfile player : playerList) {
            System.out.println("(" + label + ")\t" + player.getPlayerName());
            label++;
        }
        String playerChoice = scanner.next();
        int index = Integer.parseInt(playerChoice) - 1;
        PlayerProfile chosenPlayer = playerList.get(index);
        startCrossyRoad(chosenPlayer);
    }

    /*
     * MODIFIES: this
     * EFFECTS: asks for a username and passes it into the instantiation of
     *          a new PlayerProfile object
     *          the new object is added to the PlayerProfile list
     */
    public void createNewProfile() {
        System.out.println("Creating new profile : \nWhat is your name ?");
        String name = scanner.next();
        PlayerProfile newProfile = new PlayerProfile(name);
        System.out.println("What game will this player be playing ?");
        String choice = getGameName().toString();
        this.arcade.addPlayerProfile(newProfile, choice);
        System.out.println("New profile added for " + name + "under" + choice);
        displayOptions();
    }

    // EFFECTS: prompts user to select category and returns it
    private GameName getGameName() {
        int label = 1;
        for (GameName name : GameName.values()) {
            System.out.println("(" + label + ")" + ": " + name);
            label++;
        }
        return GameName.values()[scanner.nextInt() - 1];
    }

    /*
     * EFFECTS: instantiates a new CrossyRoadRun object
     */
    private void startCrossyRoad(PlayerProfile player) {
        try {
            new CrossyRoadRun(player);
        } catch (QuitGameException e) {
            saveArcade();
            displayOptions();
        }
    }

    // Taken from JSONSerialization
    // EFFECTS: saves the workroom to file
    private void saveArcade() {
        try {
            this.arcade.saveArcade();
            System.out.println("Saved all arcade data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

        System.out.println("Quitting Game...");
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadArcade() {
        try {
            this.arcade = jsonReader.read();
            System.out.println("Loaded Arcade from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}



