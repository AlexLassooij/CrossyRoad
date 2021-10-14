package model;

import java.util.Hashtable;
import java.util.Random;

public class CrossyRoadCar {
    private int headPositionX;
    private int headPositionY;
    private int velocity;
    private int carLength;
    private int updatesUntilNextMove;
    private String movementDirection;
    private int carIdentifier;
    private String carColour;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String[] ANSI_KEYS = new String[]{ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_BLUE, ANSI_PURPLE,
            ANSI_CYAN};

    /*
     * REQUIRES:headPositionX and headPositionY >= 0,
     *          movementDirection is one of "right" or "left"
     * MODIFIES: this
     * EFFECTS: headPositions, velocity, carLength, carIdentifier and
     *          movementDirection are set to passed arguments
     *          random ANSI Key is generated for the car's colour
     */
    public CrossyRoadCar(int headPositionX, int headPositionY, int velocity, int carLength,
                         int carIdentifier, String movementDirection) {
        this.headPositionX = headPositionX;
        this.headPositionY = headPositionY;
        this.velocity = velocity;
        this.carLength = carLength;
        this.carIdentifier = carIdentifier;
        this.movementDirection = movementDirection;
        Random rand = new Random();
        int randomIndex = rand.nextInt(ANSI_KEYS.length);
        this.carColour = ANSI_KEYS[randomIndex];
    }

    /*
     * REQUIRES: movementDirection is one of "right" or "left"
     * MODIFIES: this
     * EFFECTS: moves the car's head one unit to the left or right
     *          prints out the car's X and Y coordinates after moving
     */
    public void moveCar() {
        if (this.movementDirection.equals("left")) {
            this.headPositionX -= 1;
        } else {
            this.headPositionX += 1;
        }
        System.out.println("Car " + this.carIdentifier + "'s head is at X: "
                + this.headPositionX + " Y: " + this.headPositionY);

    }

    /*
     * EFFECTS : returns car's ANSI key that represents its colour
     */
    public String getCarColour() {
        return this.carColour;
    }

    /*
     * EFFECTS : returns car's movement directions (right or left)
     */
    public String getMovementDirection() {
        return this.movementDirection;
    }

    /*
     * EFFECTS : returns car's identifier (ID)
     */
    public int getCarIdentifier() {
        return carIdentifier;
    }

    /*
     * EFFECTS : returns car's Y coordinate on the game board
     */
    public int getCarPositionY() {
        return this.headPositionY;
    }

    /*
     * EFFECTS : returns car's X coordinate on the game board
     */
    public int getCarPositionX() {
        return this.headPositionX;
    }

    /*
     * REQUIRES: headPositionX, headPositionY and carLength are all of
     *           type Integer
     * EFFECTS: returns a Hashtable containing information about the
     *          car's position and its length
     */
    public Hashtable<String, Integer> getCarInformation() {
        Hashtable<String, Integer> infoTable = new Hashtable<>();
        infoTable.put("positionX", this.headPositionX);
        infoTable.put("positionY", this.headPositionY);
        infoTable.put("carLength", this.carLength);

        return infoTable;
    }
}
