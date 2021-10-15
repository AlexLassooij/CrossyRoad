package ui;

import model.PlayerProfile;

import java.util.*;

public class Arcade {
    private List<PlayerProfile> playerProfileList;
    private Scanner scanner;

    /*
     * MODIFIES: this
     * EFFECTS: instantiates a new PlayerProfile ArrayList
     *          instantiates a new Scanner object
     *          calls displayOptions
     */
    public Arcade() {
        this.playerProfileList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.scanner.useDelimiter("\n");
        displayOptions();
    }


    /*
     * EFFECTS: displays menu options
     *          calls handleInput to handle the user's choice
     */
    private void displayOptions() {
        System.out.println("\nSelect from options below and press enter");
        System.out.println("\t1 -> create new profile");
        System.out.println("\t2 -> play CrossyRoad");
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
        if (choice.equals("1")) {
            createNewProfile();
        } else if (choice.equals("2")) {
            System.out.println("Who is playing ?");
            int i = 1;
            for (PlayerProfile player : this.playerProfileList) {
                System.out.println("(" + i + ")\t" + player.getPlayerName());
            }
            String playerChoice = scanner.next();
            int index = Integer.parseInt(playerChoice) - 1;
            PlayerProfile chosenPlayer = this.playerProfileList.get(index);
            startCrossyRoad(chosenPlayer);
        } else {
            System.out.println("Invalid options");
            displayOptions();
        }
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
        this.playerProfileList.add(newProfile);
        System.out.println("New profile added for " + name);
        displayOptions();
    }

    /*
     * EFFECTS: instantiates a new CrossyRoadRun object
     */
    private void startCrossyRoad(PlayerProfile player) {
        new CrossyRoadRun(player);
    }

    public List<PlayerProfile> getPlayerProfileList() {
        return this.playerProfileList;
    }

}

