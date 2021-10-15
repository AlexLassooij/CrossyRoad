package ui;

import model.CrossyRoadCar;
import model.CrossyRoadGame;
import model.PlayerProfile;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class CrossyRoadRun {
    private CrossyRoadGame crossyRoadGame;
    public static final  int CONSOLE_INTERVAL = 1000;
    private Timer updateTimer;
    private boolean isTimerRunning;
    private Scanner scanner;

    /*
     * REQUIRES: player is a valid PlayerProfile object
     * MODIFIES: this
     * EFFECTS: parent class that will run the entirety of the CrossyRoad game
     *          prints out the current player's name
     *          instantiates a new CrossyRoadGame object
     *          calls addTimer
     */
    public CrossyRoadRun(PlayerProfile player) {
        System.out.println(player.getPlayerName() + " is playing !\n");
        this.scanner = new Scanner(System.in);
        this.scanner.useDelimiter("\n");
        System.out.println("Would you like to start at level 1 (1) or "
                + "continue where you left off (2) ?");
        String choice = this.scanner.next();
        boolean restartLevel = choice.equals("1");
        this.crossyRoadGame = new CrossyRoadGame(player, restartLevel);
        this.isTimerRunning = false;
        addTimer();
    }

    // This method was taken from the SpaceInvadersBase in-lecture lab code base
    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        this.updateTimer = new Timer(CONSOLE_INTERVAL, ae -> update());
        this.updateTimer.start();
        this.isTimerRunning = true;
    }

    public void stopTimer() {
        this.updateTimer.stop();
        this.isTimerRunning = false;
    }

    public boolean getTimerStatus() {
        return this.isTimerRunning;
    }

    /*
     * MODIFIES: this
     * EFFECTS: calls handleInput to request the input for the next player move
     *          checks if the player has completed the game. if so, sets the gameStatus
     *          to "COMPLETED" and calls handleGameCompletion
     *          updates the gameBoard since the player and the cars will have moved
     */
    private void update() {
        handleInput("move");
        if (this.crossyRoadGame.checkCompletion()) {
            this.crossyRoadGame.setGameStatus("COMPLETED");
            handleGameCompletion();
        }
        this.crossyRoadGame.getGameBoard().printGameBoard(
                this.crossyRoadGame.getCars(),
                this.crossyRoadGame.getCrossyRoadPlayer(),
                this.crossyRoadGame.getGameStatus());
    }

    /*
     * MODIFIES: this
     * EFFECTS: displays a certain list of options depending on the type
     *          of input that is being requested
     *          if typeOfInput is "restart":
     *              if user presses "r" will restart the game by calling
     *              the setup method setUpCrossyRoad
     *              if user presses "q", will quit the program
     *          if typeOfInput is "move" :
     *              calls movePlayer and passes the obtained input before
     *              calling moveCars
     *          else tells the user that they have provided invalid input
     *          and lets them try again by calling itself
     */
    public void handleInput(String typeOfInput) {
        displayOptions(typeOfInput);
        String inputString = this.scanner.next();
        inputString = inputString.toLowerCase(Locale.ROOT);
        // to prevent player from accidentally restarting game while still playing
        if (typeOfInput.equals("restart")) {
            if (inputString.equals("r") && this.crossyRoadGame.getGameStatus().equals("FAILED")) {
                System.out.println("Restarting game at level " + this.crossyRoadGame.getCurrentLevel() + " !\n");
                this.crossyRoadGame.setUpCrossyRoad();
            } else if (inputString.equals("q")) {
                System.exit(0);
            }
        } else if (typeOfInput.equals("move")) {
            this.crossyRoadGame.getCrossyRoadPlayer().movePlayer(inputString);
            this.crossyRoadGame.moveCars();
            if (crossyRoadGame.getGameStatus().equals("FAILED")) {
                System.out.println("Oh no ! You got hit !");
                this.crossyRoadGame.clearCars();
                System.out.println("Would you like to play again ?");
                handleInput("restart");
            }

        } else {
            System.out.println("Invalid input, please try again");
            handleInput(typeOfInput);
        }
    }

    /*
     * EFFECTS: if typeOfInput is "restart" :
     *              prints out the options for restarting or quitting
     *          if typePfInput is "move" :
     *              prints out the options for moving either
     *              up, down, left or right
     */
    private void displayOptions(String typeOfInput) {
        System.out.println("\nSelect from options below and press enter");
        if (typeOfInput.equals("restart")) {
            System.out.println("\tr -> restart");
            System.out.println("\tq -> quit");
        } else if (typeOfInput.equals("move")) {
//            System.out.println("\t⬆ -> up");
//            System.out.println("\t⬅ -> left");
//            System.out.println("\t⬇ -> down");
//            System.out.println("\t➡ -> right");
            System.out.println("\tw -> up");
            System.out.println("\ta -> left");
            System.out.println("\ts -> down");
            System.out.println("\td -> right");
        }
    }

    /*
     * EFFECTS: iteratively prints out each car's X and coordinates along
     *          with the car's identifier
     */
    private void displayCarPositions() {
        for (CrossyRoadCar nextCar: this.crossyRoadGame.getCars()) {
            System.out.println("Car " + nextCar.getCarIdentifier() + "'s head is at X: "
                    + nextCar.getCarPositionX() + " Y: " + nextCar.getCarPositionY());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: tells the user that they have completed the level
     *          asks whether they would like to continue to the next level
     *          if yes : increments the game's current level and the current player's
     *          highest achieved level
     *          calls configureDifficulty and setUpCrossyRoad to set up and set the difficulty
     *          for the next level
     *          if no : quits the program
     */
    private void handleGameCompletion() {
        System.out.println("You have completed level " + this.crossyRoadGame.getCurrentLevel()
                + " !\n"
                + "Would you like to continue to the next level (y/n) ?\n");
        String choice = this.scanner.next();
        choice = choice.toLowerCase(Locale.ROOT);
        if (choice.equals("y")) {
            this.crossyRoadGame.increaseLevel();
            this.crossyRoadGame.getArcadePlayer().increaseLevelAchieved();
            this.crossyRoadGame.configureDifficulty();
            this.crossyRoadGame.setUpCrossyRoad();

        } else if (choice.equals("n")) {
            this.crossyRoadGame.setGameStatus("QUIT");
            System.exit(0);
        } else {
            System.out.println("Invalid key Press");

        }
    }

//    private class KeyHandler extends KeyAdapter {
//        @Override
//        public void keyPressed(KeyEvent e) {
//            crossyRoadGame.keyPressed(e.getKeyCode());
//        }
//    }
}
