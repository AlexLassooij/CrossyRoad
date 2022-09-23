package model;

import ui.CrossyRoadRun;

import java.awt.*;

public class CrossyRoadCar {
    private int headPositionX;
    private final int headPositionY;
    private final int velocity;
    private final int carLength;
    private int delayTime;
    private final String movementDirection;
    private final int carIdentifier;
    private final Color carColour;
    private EventLog logger;

    /*
     * REQUIRES:headPositionX and headPositionY >= 0,
     *          movementDirection is one of "right" or "left"
     * MODIFIES: this
     * EFFECTS: headPositions, velocity, carLength, carIdentifier and
     *          movementDirection are set to passed arguments
     *          random ANSI Key is generated for the car's colour
     */
    public CrossyRoadCar(int headPositionX, int headPositionY, int velocity, int carLength,
                         int carIdentifier, int delayTime, String movementDirection) {
        this.headPositionX = headPositionX;
        this.headPositionY = headPositionY;
        this.velocity = velocity;
        this.carLength = carLength;
        this.carIdentifier = carIdentifier;
        this.movementDirection = movementDirection;
        this.delayTime = delayTime;
        this.carColour = Colors.getRandomColor();
        this.logger = EventLog.getInstance();
    }

    /*
     * REQUIRES: movementDirection is one of "right" or "left"
     * MODIFIES: this
     * EFFECTS: moves the car's head one unit to the left or right
     */
    public void moveCar() {
        if (delayTime > 0) {
            delayTime -= CrossyRoadRun.MOVE_MULTIPLIER;
            return;
        }
        if (this.movementDirection.equals("left")) {
            this.headPositionX -= this.velocity * CrossyRoadRun.MOVE_MULTIPLIER;
        } else {
            this.headPositionX += this.velocity * CrossyRoadRun.MOVE_MULTIPLIER;
        }
    }

    /*
     * EFFECTS : returns car's ANSI key that represents its colour
     */
    public Color getCarColour() {
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
     * EFFECTS : returns car's identifier (ID)
     */
    public int getCarLength() {
        return carLength;
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

    public int getVelocity() {
        return this.velocity;
    }

    public void setDelayTime(int time) {
        this.delayTime = time;
    }
}
