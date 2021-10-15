package ui;

import model.CrossyRoadCar;
import model.CrossyRoadPlayer;

import java.util.Hashtable;
import java.util.List;

public class GameBoardGenerator {
    private int height;
    private int width;
    private String[][] gameBoard;
    public static final String ANSI_RESET = "\u001B[0m";



    /*
     * MODIFIES: this
     * EFFECTS: sets the height and width of the gameBoard
     *          instantiates a new 2D String array
     */
    public GameBoardGenerator(int height, int width) {
        this.height = height;
        this.width = width;
        this.gameBoard = new String[height][width];
        resetBoard();
    }

    /*
     * MODIFIES: this
     * EFFECTS: populates the entire board with a default "-" character
     */
    private void resetBoard() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.gameBoard[i][j] = "-";
            }
        }
    }

    /*
     * REQUIRES: height and width and both > 0
     * MODIFIES: this
     * EFFECTS: prints the gameBoard containing all cars and the player in the standard output
     */
    public void printGameBoard(List<CrossyRoadCar> cars, CrossyRoadPlayer player, String gameStatus) {
        populateGameBoard(cars, player, gameStatus);
        StringBuilder gameBoardRow;
        for (int i = this.height - 1; i >= 0; i--) {
            gameBoardRow = new StringBuilder();
            for (int j = 0; j < this.width; j++) {
                gameBoardRow.append(this.gameBoard[i][j]);
            }
            System.out.println(gameBoardRow);
        }

    }

    /*
     * MODIFIES: this
     * EFFECTS: populates the gameBoard with each car and the current player
     *          does not print the player if they completed the level, since
     *          the player will be out of bounds
     */
    private void populateGameBoard(List<CrossyRoadCar> cars, CrossyRoadPlayer player, String gameStatus) {
        resetBoard();
        for (CrossyRoadCar nextCar : cars) {
            setCars(nextCar);
        }
        if (!gameStatus.equals("COMPLETED")) {
            setPlayer(player);
        }

    }

    /*
     * MODIFIES: this
     * EFFECTS: prints a coloured "o" for each position that the car
     *          occupies on the gameBoard
     */
    private void setCars(CrossyRoadCar car) {
        Hashtable<String, Integer> infoTable = car.getCarInformation();
        int positionX = infoTable.get("positionX");
        int positionY = infoTable.get("positionY");
        int carLength = infoTable.get("carLength");
        String movementDirection = car.getMovementDirection();
        String carColour = car.getCarColour();
        for (int i = 0;i < carLength; i++) {
            if (movementDirection.equals("right") && (positionX - i) < this.width && (positionX - i) >= 0) {
                this.gameBoard[positionY][positionX - i] = carColour + "o" + ANSI_RESET;
            } else if (movementDirection.equals("left") && (positionX + i) < this.width && (positionX + i) >= 0) {
                this.gameBoard[positionY][positionX + i] = carColour + "o" + ANSI_RESET;
            } else {
                System.out.println("Invalid Parameters or section of car not in frame");
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: prints an "x" on the position that the player
     *          occupies on the gameBoard
     */
    private void setPlayer(CrossyRoadPlayer player) {
        int positionX = player.getPositionX();
        int positionY = player.getPositionY();
        this.gameBoard[positionY][positionX] = "x";
    }
}
