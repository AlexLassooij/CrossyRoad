package model;


import ui.GameBoardGenerator;

import java.util.*;

public class CrossyRoadGame {
    public static final int GAME_WIDTH = 5;
    public static final int MAX_CAR_LENGTH = 3;
    // minimum 4, larger number means slower
    // this will come into play later when the cars move independently
    // of the console's control
    private int movementInterval;
    private int gameHeight;
    private int currentLevel;
    private int numCars;
    private String gameStatus;
    private CrossyRoadPlayer crossyRoadPlayer;
    private PlayerProfile arcadePlayer;
    private List<CrossyRoadCar> cars;
    private GameBoardGenerator gameBoard;

    // START : game waiting to be initialized
    // ONGOING : player is playing
    // FAILED : player lost the game --> ask for reset or quit
    // COMPLETED : players has won the game
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
        this.gameHeight = 5 + (2 * (this.currentLevel - 1));
        this.numCars = 3 + (this.currentLevel - 1);
        this.gameStatus = "START";
        this.movementInterval = 1000 - (50 * (this.currentLevel - 1));
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
        this.gameStatus = "ONGOING";
        this.crossyRoadPlayer = new CrossyRoadPlayer(this.arcadePlayer.getPlayerName());
        clearCars();
        generateCars(this.numCars);
        this.gameBoard = new GameBoardGenerator(this.gameHeight, GAME_WIDTH);
        displayCarPositions();
        this.gameBoard.printGameBoard(this.cars, this.crossyRoadPlayer, this.gameStatus);
    }

    /*
     * EFFECTS: iteratively prints out each car's X and coordinates along
     *          with the car's identifier
     */
    private void displayCarPositions() {
        for (CrossyRoadCar nextCar: this.cars) {
            System.out.println("Car " + nextCar.getCarIdentifier() + "'s head is at X: "
                    + nextCar.getCarPositionX() + " Y: " + nextCar.getCarPositionY());
        }
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
        List<Integer> carLengthList = generateCarLengthList();
        for (int i = 0;i < numCars;i++) {
            CrossyRoadCar car = createCar(coordinateListY, carLengthList, cars.size() + 1);
            this.cars.add(car);
            if (coordinateListY.size() == 0) {
                coordinateListY = generateCoordinateListY();
            }
        }
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
    public CrossyRoadCar replaceCar(int carIdentifier, int formerPositionY) {
        List<Integer> coordinateListY = generateCoordinateListY();
        List<Integer> carLengthList = generateCarLengthList();
        CrossyRoadCar car = createCar(coordinateListY, carLengthList, carIdentifier);
        // if there are many cars, we would like to generate a new car in the same
        // row to avoid clustering on same row
        if (numCars >= this.gameHeight) {
            car.setHeadPositionY(formerPositionY);
        }
        return car;
    }


    private CrossyRoadCar createCar(List<Integer> coordinateListY, List<Integer> carLengthList, int carIdentifier) {
        Hashtable<String, Integer> infoTable = generateCarInfo(coordinateListY, carLengthList);
        int positionX = infoTable.get("positionX");
        int positionY = infoTable.get("positionY");
        int carLength = infoTable.get("carLength");
        int velocity = infoTable.get("velocity");
        String movementDirection;
        if (positionX == 0) {
            movementDirection = "right";
        } else {
            movementDirection = "left";
        }
        return new CrossyRoadCar(positionX, positionY, velocity, carLength, carIdentifier,
                movementDirection);
    }


    /*
     * REQUIRES: gameHeight >= 1
     * EFFECTS: generates a list of integers ranging from 0 to gameHeight - 1
     */
    public List<Integer> generateCoordinateListY() {
        List<Integer> coordinateListY = new ArrayList<>();
        for (int i = 0; i < this.gameHeight; i++) {
            coordinateListY.add(i);
        }
        return coordinateListY;
    }

    /*
     * EFFECTS: generates a list of integers ranging from 1 to MAX_CAR_LENGTH
     */
    public List<Integer> generateCarLengthList() {
        List<Integer> carLengthList = new ArrayList<>();
        for (int i = 0; i < MAX_CAR_LENGTH; i++) {
            carLengthList.add(i + 1);
        }
        return carLengthList;
    }

    /*
     * REQUIRES: gameHeight >= 1
     * EFFECTS: generates a hashtable containing randomized integer
     *          values for a new car's X and Y coordinates, length and
     *          velocity
     *          uses a Random object to generate random indices to choose
     *          values from the Y coordinate and carLength lists
     */
    public Hashtable<String, Integer> generateCarInfo(List<Integer> coordinateListY, List<Integer> carLengthList) {
        Hashtable<String, Integer> infoTable = new Hashtable<>();
        Random rand = new Random();
        int carLength = carLengthList.get(rand.nextInt(carLengthList.size()));
        int positionYIndex = rand.nextInt(coordinateListY.size());
        int positionY = coordinateListY.get(positionYIndex);
        // to avoid more than one car in one row
        coordinateListY.remove(positionYIndex);
        int positionX = Arrays.asList(0, GAME_WIDTH - 1).get(rand.nextInt(2));
        // maximum velocity increases with level
        int velocity = rand.nextInt(1 + this.currentLevel / 2);
        if (velocity <= 0) {
            velocity = 1;
        }

        infoTable.put("positionX", positionX);
        infoTable.put("positionY", positionY);
        infoTable.put("carLength", carLength);
        infoTable.put("velocity", velocity);
        return infoTable;
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

        for (int i = 0; i < this.numCars; i++) {
            CrossyRoadCar nextCar = this.cars.get(i);
            nextCar.moveCar();
            if (checkCollision(nextCar)) {
                this.gameStatus = "FAILED";
                break;
            }
            // if car is out of boundary, remove from game and create a new one
            if (isCarOutOfBoundary(nextCar)) {
                int carIdentifier = nextCar.getCarIdentifier();
                int formerPositionY = nextCar.getCarPositionY();
                CrossyRoadCar replacementCar = replaceCar(carIdentifier, formerPositionY);
                this.cars.set(i, replacementCar);
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
        Hashtable<String, Integer> infoTable = car.getCarInformation();
        int carPositionX = infoTable.get("positionX");
        int carPositionY = infoTable.get("positionY");
        int carLength = infoTable.get("carLength");

        if (carPositionY == this.crossyRoadPlayer.getPositionY()) {
            int playerPositionX = this.crossyRoadPlayer.getPositionX();
            String movementDirection = car.getMovementDirection();
            int currentPositionBeingCompared;
            for (int i = 0;i < carLength;i++) {
                // if car is moving right, then the other sections of the car will be
                // to the left of the head. If it is moving left, then the other sections
                // will be to the right
                if (movementDirection.equals("right")) {
                    currentPositionBeingCompared = carPositionX - i;
                } else {
                    currentPositionBeingCompared = carPositionX + i;
                }
                if (currentPositionBeingCompared == playerPositionX) {
                    System.out.println("Collision");
                    return true;
                }
            }
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
        Hashtable<String, Integer> infoTable = car.getCarInformation();
        int carPositionX = infoTable.get("positionX");
        int carLength = infoTable.get("carLength");

        // right-bound car : headPositionX will be > GAME_WIDTH
        // check if left-hand sections of car are still within boundaries
        if (carPositionX >= GAME_WIDTH || carPositionX < 0) {
            if (carLength == 1) {
                return true;
            }
            String movementDirection = car.getMovementDirection();
            if (movementDirection.equals("right")) {
                return carPositionX - (carLength - 1) >= GAME_WIDTH;
            } else if (movementDirection.equals("left")) {
                return carPositionX + (carLength - 1) < 0;
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

    // Adapted from Space Invaders
    // will be used in later parts of the term project, as key listeners only work on Java Components

//    public void keyPressed(int keyCode) {
//        if (keyCode == KeyEvent.VK_R && this.gameStatus.equals("FAILED")) {
//            // make setup fully able to restart a game from scratch
//            System.out.println("Restarting game at level " + this.currentLevel + " !\n");
//            setUpCrossyRoad();
//        } else if (keyCode == KeyEvent.VK_X) {
//            System.exit(0);
//        } else {
//            this.crossyRoadPlayer.movePlayer(keyCode);
//        }
//    }


    // helper functions for testing
    // may be of use later on
    public int getMovementInterval() {
        return this.movementInterval;
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

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameBoardGenerator getGameBoard() {
        return this.gameBoard;
    }

    public CrossyRoadPlayer getCrossyRoadPlayer() {
        return this.crossyRoadPlayer;
    }

}
