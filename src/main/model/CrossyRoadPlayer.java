package model;

import ui.GameBoard;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CrossyRoadPlayer {
    private int positionX;
    private int positionY;
    private final String playerName;
    private final Color playerColor = Colors.getRandomColor();
    // private EventLog logger;

    public CrossyRoadPlayer(String playerName) {
        this.positionX = ((CrossyRoadGame.GAME_WIDTH / 100) / 2) * 100;
        this.positionY = 0;
        this.playerName = playerName;
        // this.logger = EventLog.getInstance();
    }

    /*
     * MODIFIES: this
     * EFFECTS: moves the player up, down, left or right by one unit according to "directionOfMovement"
     */
    public void movePlayer(int directionOfMovement) {
        if (directionOfMovement == KeyEvent.VK_W || directionOfMovement == KeyEvent.VK_S
                || directionOfMovement == KeyEvent.VK_UP || directionOfMovement == KeyEvent.VK_DOWN) {
            this.positionY = checkBoundariesAndMove(directionOfMovement, this.positionY);
        } else {
            this.positionX = checkBoundariesAndMove(directionOfMovement, this.positionX);
        }
        System.out.println("Player now at X: " + positionX + " Y: " + positionY);
        // logger.logEvent(new Event("Player moved to X: " + this.positionX + " Y: " + positionY));
    }

    /*
     * EFFECTS: returns the modified position if is within the game's boundaries
     *          if the modified position is outside the game's boundaries, it returns
     *          the previous position
     */

    // requires the main game to check for a game win
    public int checkBoundariesAndMove(int keyCode, int position) {
        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                return position + GameBoard.PIXELS_PER_UNIT;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (position == 0) {
                    return position;
                } else {
                    return position - GameBoard.PIXELS_PER_UNIT;
                }
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if (position >= CrossyRoadGame.GAME_WIDTH - GameBoard.PIXELS_PER_UNIT) {
                    return CrossyRoadGame.GAME_WIDTH - GameBoard.PIXELS_PER_UNIT;
                } else {
                    return position + GameBoard.PIXELS_PER_UNIT;
                }
            default:
                return position;
        }
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getCrossyRoadPlayerName() {
        return this.playerName;
    }

    public Color getPlayerColor() {
        return this.playerColor;
    }
}
