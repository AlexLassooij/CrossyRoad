package model;

import ui.BackGroundPanel;
import ui.CrossyRoadRun;
import ui.DisplayController;
import ui.GameBoard;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CrossyRoadGame {
    public static final int GAME_WIDTH = 1300;
    public static final int INIT_HEIGHT = 900;
    public static final int MAX_CAR_LENGTH = 3;
    public static final int MIN_CAR_LENGTH = 1;
    public static final int MAX_VELOCITY = 4;
    private int gameHeight;
    private int currentLevel;
    private int numRowsWithCars;
    private int carsPerRow;
    private String gameStatus;
    private final CrossyRoadPlayer crossyRoadPlayer;
    private final PlayerProfile arcadePlayer;
    private List<CrossyRoadCar> cars;
    private List<Integer> carCoordinates = new ArrayList<>();
    private List<Integer> roadCoordinates = new ArrayList<>();
    private static CrossyRoadGame theGame;

    // START : game waiting to be initialized
    // ONGOING : player is playing
    // FAILED : player lost the game --> ask for reset or quit : player has quit the game

    /*
     * REQUIRES: player is a PlayerProfile object
     * MODIFIES: this
     * EFFECTS: assigns player to member variable arcadePlayer
     *          asks whether to start at level one or highest achieved
     *          level and sets the current level accordingly
     *          instantiates a new ArrayList for cars
     *          calls configureDifficulty and setUpCrossyRoad
     */
    public CrossyRoadGame(PlayerProfile player, boolean restartLevel) {
        this.arcadePlayer = player;
        if (restartLevel) {
            this.currentLevel = 1;
        } else {
            this.currentLevel = this.arcadePlayer.getLevelAchieved();
        }
        this.cars = new ArrayList<>();
        theGame = this;
        this.crossyRoadPlayer = new CrossyRoadPlayer(this.arcadePlayer.getPlayerName());
        configureDifficulty();
        setUpCrossyRoad();
    }

    public static CrossyRoadGame getInstance() {
        return theGame;
    }

    /*
     * REQUIRES: currentLevel >= 1
     * MODIFIES: this
     * EFFECTS: sets the game's difficulty according to current level
     *  increments gameHeight by 2 with each level increase
     *  increments numCars by one with each level increase
     *  decreases the movement interval by 50ms  with each level increase
     */
    public void configureDifficulty() {
        this.gameHeight = (INIT_HEIGHT / 100 + (2 * (this.currentLevel - 1))) * 100;
        this.numRowsWithCars = 5 + (this.currentLevel - 1);
        this.carsPerRow = Math.min((1 + (this.currentLevel / 2)), 4);
    }

    /*
     * REQUIRES: arcadePlayer is a valid PlayerProfile object
     * MODIFIES: this
     * EFFECTS: creates a new CrossyRoadPlayer object
     *          clears all cars from the ArrayList "cars" and adds new cars to it
     *          creates a new GameBoardGenerator object and passes the gameBoards' height and width
     *          calls displayCarPositions
     *          calls the gameBoard's printGameBoard method
     */
    public void setUpCrossyRoad() {
        this.crossyRoadPlayer.setPositionY(0);
        clearCars();
        roadCoordinates.clear();
        carCoordinates.clear();
        generateCars(this.numRowsWithCars);
        DisplayController.getInstance().updateDisplayIndices();
        BackGroundPanel.getInstance().repaint();
        this.gameStatus = "ONGOING";
    }

    /*

     * MODIFIES: this
     * EFFECTS: iteratively instantiates new CrossyRoadCar objects, one row
                at a time. If the difficulty of the current level allows,
                more than one car will be generated per row
     *
     *          adds the new CrossyRoadObject to cars
     *
     */
    public void generateCars(int numCars) {
        generateCarCoordinates();
        for (int i = 0;i < numCars;i++) {
            generateCarRow();
        }
    }

    private void generateCarRow() {
        int positionX;
        String movementDirection = getRandomDirection();
        int positionY = getRandomYPosition();
        int velocity = getRandomVelocity();
        int delayTime = GAME_WIDTH / (this.carsPerRow * 2) + (int)(Math.random()
                * (GAME_WIDTH / (2 * this.carsPerRow)));
        for (int k = 0; k < carsPerRow; k++) {
            int carLength = getRandomCarLength();
            if (movementDirection.equals("right")) {
                positionX = 0;
            } else {
                positionX = GAME_WIDTH;
            }

            cars.add(new CrossyRoadCar(positionX, positionY, velocity, carLength, cars.size() + 1,
                    delayTime * (k), movementDirection));
        }
    }

    /*
     * REQUIRES: gameHeight >= 1
     * EFFECTS: generates a list of integers ranging from 0 to gameHeight - 1
     */
    public void generateCarCoordinates() {
        // i = 1 to guarantee that cars won't spawn in the bottom row
        for (int i = 0; i < this.gameHeight; i += GameBoard.PIXELS_PER_UNIT) {
            carCoordinates.add(i);
        }
    }

    /*
     * REQUIRES: gameHeight >= 1
     * EFFECTS: generates a hashtable containing randomized integer
     *          values for a new car's X and Y coordinates, length and
     *          velocity
     *          uses a Random object to generate random indices to choose
     *          values from the Y coordinate and carLength lists
     */

    public int getRandomYPosition() {
        int positionYIndex = 1 + (int)(Math.random() * (carCoordinates.size() - 1));
        int positionY = carCoordinates.get(positionYIndex);
        carCoordinates.remove(positionYIndex);
        roadCoordinates.add(positionY);
        return positionY;
    }

    public String getRandomDirection() {
        return Arrays.asList("right", "left").get((int)(Math.random() * 2));
    }

    public int getRandomVelocity() {
        return Math.min(MAX_VELOCITY, 1 + (int)(Math.random() * (1 + this.currentLevel / 2)));
    }

    public int getRandomCarLength() {
        return MIN_CAR_LENGTH + (int)(Math.random() * MAX_CAR_LENGTH - MIN_CAR_LENGTH + 1);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a new car with same velocity, y position, movement direction
     *          and identifier as prev car. The car spawns at the default spawning point
     *          corresponding to its movement direction
     *
     *          returns a new CrossyRoadCar object
     */
    public CrossyRoadCar replaceCar(CrossyRoadCar prevCar) {
        String movementDirection = prevCar.getMovementDirection();

        int carLength = getRandomCarLength();
        int positionY = prevCar.getCarPositionY();
        int velocity = prevCar.getVelocity();
        int carIdentifier = prevCar.getCarIdentifier();
        int positionX;
        if (movementDirection.equals("right")) {
            positionX = 0;
        } else {
            positionX = GAME_WIDTH;
        }
        return new CrossyRoadCar(positionX, positionY, velocity, carLength, carIdentifier,
                0, movementDirection);
    }

    /*
     * REQUIRES: numCars >= 1
     * MODIFIES: this
     * EFFECTS: for each car in cars, calls the car's moveCar method in order
     *          to move it to its next position.
     *          after moving, checks whether the car has collided with the player
     *          if there is a collision, the game is ended by setting the status
     *          to FAILED and clearing all cars.
     *          The user is then asked if they would like to play again
     *          handleInput is called to handle the user's choice
     *
     *          if there is no collision, isCarOutOfBoundary is called to
     *          check if the entire length of the car has left the gameBoard
     *          if so, replaceCar is called in order to replace it
     */
    public void moveCars() {
        for (CrossyRoadCar nextCar : this.cars) {
            nextCar.moveCar();
            if (checkCollision(nextCar)) {
                this.gameStatus = "FAILED";
            }
        }
        handleCarBoundaries();
    }

    /*
     * REQUIRES: car is a valid CrossyRoad object
     * EFFECTS: returns true if any part of the car shares the same coordinates
     *          as the player
     *          returns false otherwise
     */
    public boolean checkCollision(CrossyRoadCar car) {
        int boxSize = GameBoard.PIXELS_PER_UNIT;
        int carLength = car.getCarLength();
        int carPositionX = car.getCarPositionX();
        if (car.getMovementDirection().equals("right")) {
            carPositionX -= carLength * boxSize;
        }
        int carPositionY = car.getCarPositionY();
        // position as displayed on JPanel
        int displayPositionY = this.gameHeight - carPositionY;
        if (carPositionY == this.crossyRoadPlayer.getPositionY()) {
            int playerPositionX = this.crossyRoadPlayer.getPositionX();
            Rectangle crossyRoadPlayerBoundary = new Rectangle(playerPositionX,
                    displayPositionY - boxSize, boxSize, boxSize);
            Rectangle carBoundary = new Rectangle(carPositionX,
                    displayPositionY - boxSize, carLength * boxSize,
                    boxSize);
            return carBoundary.intersects(crossyRoadPlayerBoundary);
        }
        return false;
    }


    /*
     * REQUIRES: car is a valid CrossyRoad object
     * EFFECTS: replaces car if car's head is out of boundary and length is 1
     *          if length > 1, spawns a new car but waits until the entire car is out
     *          boundary of game
     *          we want to spawn new cars when the head leaves the boundary
     *          so that cars are spawned at consistent interval and don't cluster together
     *          the game's boundaries
     *          returns false otherwise
     */
    public void handleCarBoundaries() {
        List<CrossyRoadCar> modifiedList = new ArrayList<>(this.cars);
        // right-bound car : headPositionX will be > GAME_WIDTH
        // check if left-hand sections of car are still within boundaries
        // cars start with head at 0 or GAME_WIDTH
        for (CrossyRoadCar car : this.cars) {
            int carPositionX = car.getCarPositionX();
            int carLength = car.getCarLength();
            if (carPositionX == GAME_WIDTH + GameBoard.PIXELS_PER_UNIT
                    || carPositionX == -1 * GameBoard.PIXELS_PER_UNIT) {
                if (carLength == 1) {
                    modifiedList.remove(car);
                }
                modifiedList.add(replaceCar(car));
            }
            // remove cars with length > 1 when they full leave the game's boundary
            if (carPositionX - carLength * GameBoard.PIXELS_PER_UNIT >= GAME_WIDTH
                    || carPositionX + carLength * GameBoard.PIXELS_PER_UNIT < 0) {
                modifiedList.remove(car);
            }
        }
        this.cars = modifiedList;
    }

    /*
     * EFFECTS: returns true if player's Y coordinate is equal to or larger
     *          than the gameBoard's height
     *          returns false otherwise
     */
    public boolean checkCompletion() {
        return this.crossyRoadPlayer.getPositionY() >= this.gameHeight;
    }

    public int getGameHeight() {
        return this.gameHeight;
    }

    public String getGameStatus() {
        return this.gameStatus;
    }

    public PlayerProfile getArcadePlayer() {
        return this.arcadePlayer;
    }

    public CrossyRoadPlayer getCrossyRoadPlayer() {
        return this.crossyRoadPlayer;
    }

    public List<CrossyRoadCar> getCars() {
        return this.cars;
    }

    public int getNumRowsWithCars() {
        return this.numRowsWithCars;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public List<Integer> getRoadCoordinates() {
        return roadCoordinates;
    }

    public List<Integer> getCarCoordinates() {
        return carCoordinates;
    }

    public void clearCars() {
        this.cars.clear();
    }

    public void increaseLevel() {
        this.currentLevel += 1;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
