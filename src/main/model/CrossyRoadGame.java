package model;

import ui.GameBoard;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CrossyRoadGame {
    public static final int GAME_WIDTH = 1300;
    public static final int MAX_CAR_LENGTH = 3;
    public static final int MIN_CAR_LENGTH = 1;
    public static final int MAX_VELOCITY = 4;
    private int gameHeight;
    private int currentLevel;
    private int numCars;
    private int carsPerRow;
    private String gameStatus;
    private CrossyRoadPlayer crossyRoadPlayer;
    private final PlayerProfile arcadePlayer;
    private List<CrossyRoadCar> cars;

    // START : game waiting to be initialized
    // ONGOING : player is playing
    // FAILED : player lost the game --> ask for reset or quit
    // QUIT : player has quit the game

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
//        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//        Console con = System.console();
        this.arcadePlayer = player;
        if (restartLevel) {
            this.currentLevel = 1;
        } else {
            this.currentLevel = this.arcadePlayer.getLevelAchieved();
        }
        this.cars = new ArrayList<>();
        configureDifficulty();
        setUpCrossyRoad();
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
        this.gameHeight = (7 + (2 * (this.currentLevel - 1))) * 100;
        this.numCars = 5 + (this.currentLevel - 1);
        this.carsPerRow = Math.min((1 + (this.currentLevel / 3)), 3);
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
        this.crossyRoadPlayer = new CrossyRoadPlayer(this.arcadePlayer.getPlayerName());
        clearCars();
        generateCars(this.numCars);
        this.gameStatus = "ONGOING";
    }


    /*
     * REQUIRES: numCars >= 1
     * MODIFIES: this
     * EFFECTS: creates a new Y coordinate list that will hold
     *          all non-occupied Y coordinates
     *          creates a new car length list that will hold
     *          all allowed car lengths
     *          iteratively instantiates new CrossyRoadCar objects:
     *              generates a new Hashtable with randomized car data that
     *              is assigned to the starting position, car length and velocity
     *              and sets the movementDirection to "right" if car is starting
     *              on left side or to "left" if car is starting on right side
     *          adds the new CrossyRoadObject to cars
     *          if all possible Y coordinates have been taken, a new list
     *          is created
     *
     */
    public void generateCars(int numCars) {
        List<Integer> coordinateListY = generateCoordinateListY();
        for (int i = 0;i < numCars;i++) {
            generateCarRow(coordinateListY);
        }
//            if (coordinateListY.size() == 0) {
//                coordinateListY = generateCoordinateListY();
//            }
    }


    /*
     * REQUIRES:  0 <= formerPositionY < gameHeight
     * MODIFIES: this
     * EFFECTS: creates a new Y coordinate list that will hold
     *          all non-occupied Y coordinates
     *          creates a new car length list that will hold
     *          all allowed car lengths
     *          generates a new car with this information
     *          if the number of cars exceeds the game's height (impossible with
     *          current configuration but subject to change), positionY will
     *          be the same as the car it is replacing, or else positionY will
     *          be assigned a randomized value
     *
     *          returns a new CrossyRoadCar object
     */
    public CrossyRoadCar replaceCar(CrossyRoadCar prevCar) {
        String movementDirection = prevCar.getMovementDirection();
        int positionX;
        if (movementDirection.equals("right")) {
            positionX = GameBoard.PIXELS_PER_UNIT * -1;
        } else {
            positionX = GAME_WIDTH;
        }
        int carLength = getRandomCarLength();
        int positionY = prevCar.getCarPositionY();
        int velocity = prevCar.getVelocity();
        int carIdentifier = prevCar.getCarIdentifier();
        return new CrossyRoadCar(positionX, positionY, velocity, carLength, carIdentifier,
                0, movementDirection);
    }


    private void generateCarRow(List<Integer> coordinateListY) {
        int positionX;
        String movementDirection = getRandomDirection();
        int positionY = getRandomYPosition(coordinateListY);
        int velocity = getRandomVelocity();
        for (int k = 0; k < carsPerRow; k++) {
            int carLength = getRandomCarLength();
            if (movementDirection.equals("right")) {
                positionX = carLength * -1 * GameBoard.PIXELS_PER_UNIT;
            } else {
                positionX = GAME_WIDTH + carLength  * GameBoard.PIXELS_PER_UNIT;
            }
            cars.add(new CrossyRoadCar(positionX, positionY, velocity, carLength, cars.size() + 1,
                    (GAME_WIDTH / carsPerRow) * k, movementDirection));
        }
    }

    /*
     * REQUIRES: gameHeight >= 1
     * EFFECTS: generates a list of integers ranging from 0 to gameHeight - 1
     */
    public List<Integer> generateCoordinateListY() {
        List<Integer> coordinateListY = new ArrayList<>();
        for (int i = 0; i < this.gameHeight; i += GameBoard.PIXELS_PER_UNIT) {
            coordinateListY.add(i);
        }
        return coordinateListY;
    }

    /*
     * REQUIRES: gameHeight >= 1
     * EFFECTS: generates a hashtable containing randomized integer
     *          values for a new car's X and Y coordinates, length and
     *          velocity
     *          uses a Random object to generate random indices to choose
     *          values from the Y coordinate and carLength lists
     */

    public int getRandomYPosition(List<Integer> coordinateListY) {
        int positionYIndex = (int)(Math.random() * (coordinateListY.size()));
        int positionY = coordinateListY.get(positionYIndex);
        coordinateListY.remove(positionYIndex);
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

        for (int i = 0; i < this.cars.size(); i++) {
            CrossyRoadCar nextCar = this.cars.get(i);
            nextCar.moveCar();
            if (checkCollision(nextCar)) {
                this.gameStatus = "FAILED";

            }
            // if car is out of boundary, remove from game and create a new one
            if (isCarOutOfBoundary(nextCar)) {
                this.cars.set(i, replaceCar(nextCar));
            }
        }
    }

    /*
     * REQUIRES: car is a valid CrossyRoad object
     * EFFECTS: returns true if any part of the car shares the same coordinates
     *          as the player
     *          returns false otherwise
     */
    public boolean checkCollision(CrossyRoadCar car) {
        int boxSize = GameBoard.PIXELS_PER_UNIT;
        int carPositionX = car.getCarPositionX();
        int carPositionY = car.getCarPositionY();
        // position as displayed on JPanel
        int displayPositionY = this.gameHeight - carPositionY;
        int carLength = car.getCarLength();
        if (carPositionY == this.crossyRoadPlayer.getPositionY()) {
            int playerPositionX = this.crossyRoadPlayer.getPositionX();
            Rectangle crossyRoadPlayerBoundary = new Rectangle(playerPositionX,
                    displayPositionY - boxSize, boxSize, boxSize);
            Rectangle carBoundary = new Rectangle(carPositionX - (carLength - 1) * boxSize,
                    displayPositionY - boxSize, carLength * boxSize,
                    boxSize);
            if (car.getMovementDirection().equals("left")) {
                carBoundary = new Rectangle(carPositionX,
                        displayPositionY - boxSize, carLength * boxSize,
                        boxSize);
            }
            return carBoundary.intersects(crossyRoadPlayerBoundary);
        }
        return false;
    }


    /*
     * REQUIRES: car is a valid CrossyRoad object
     * EFFECTS: returns true if all parts of the car are no longer within
     *          the game's boundaries
     *          returns false otherwise
     */
    public boolean isCarOutOfBoundary(CrossyRoadCar car) {
        int carPositionX = car.getCarPositionX();
        int carLength = car.getCarLength();

        // right-bound car : headPositionX will be > GAME_WIDTH
        // check if left-hand sections of car are still within boundaries
        if (carPositionX >= GAME_WIDTH || carPositionX < 0) {
            if (carLength == 1) {
                return true;
            }
            String movementDirection = car.getMovementDirection();
            if (movementDirection.equals("right")) {
                return carPositionX - (carLength - 1) * GameBoard.PIXELS_PER_UNIT >= GAME_WIDTH;
            } else if (movementDirection.equals("left")) {
                return carPositionX + (carLength) * GameBoard.PIXELS_PER_UNIT < 0;
            }
        }
        return false;
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

    public int getNumCars() {
        return this.numCars;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void clearCars() {
        this.cars.clear();
    }

    public void increaseLevel() {
        this.currentLevel += 1;
    }

    public void setNumCars(int numCars) {
        this.numCars = numCars;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }


}
