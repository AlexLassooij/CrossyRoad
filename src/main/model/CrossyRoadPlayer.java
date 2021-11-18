package model;

import ui.GameBoard;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CrossyRoadPlayer {
    private int positionX;
    private int positionY;
    private String playerName;
    private Color playerColor = Colors.getRandomColor();

    public CrossyRoadPlayer(String playerName) {
        this.positionX = ((CrossyRoadGame.GAME_WIDTH / 100) / 2) * 100;
        this.positionY = 0;
        this.playerName = playerName;
    }

    /*
     * MODIFIES: this
     * EFFECTS: moves the player up, down, left or right by one unit according to "directionOfMovement"
     */
    public void movePlayer(int directionOfMovement) {
        if (directionOfMovement == KeyEvent.VK_W || directionOfMovement == KeyEvent.VK_S) {
            this.positionY = checkBoundariesAndMove(directionOfMovement, this.positionY);
            System.out.println(positionY);
        } else {
            this.positionX = checkBoundariesAndMove(directionOfMovement, this.positionX);
            System.out.println(positionX);

        }
    }

//    public void movePlayer(int keyCode) {
//        if (keyCode == KeyEvent.VK_UP  || keyCode == KeyEvent.VK_DOWN) {
//            this.positionY = checkBoundariesAndMove(keyCode, this.positionY);
//        } else {
//            this.positionX = checkBoundariesAndMove(keyCode, this.positionX);
//        }
//    }

    /*
     * EFFECTS: returns the modified position if is within the game's boundaries
     *          if the modified position is outside the game's boundaries, it returns
     *          the previous position
     */
//    public int checkBoundariesAndMove(String directionOfMovement, int position) {
//        switch (directionOfMovement) {
//            case "w":
//                return position + 1;
//            case "s":
//            case "a":
//                if (position == 0) {
//                    return position;
//                } else {
//                    return position - 1;
//                }
//            case "d":
//                if (position == CrossyRoadGame.GAME_WIDTH - 1) {
//                    return position;
//                } else {
//                    return position + 1;
//                }
//            default:
//                return position;
//        }
//    }

    // requires the main game to check for a game win
    private int checkBoundariesAndMove(int keyCode, int position) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                return position + GameBoard.PIXELS_PER_UNIT;
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
                if (position == 0) {
                    return position;
                } else {
                    return position - GameBoard.PIXELS_PER_UNIT;
                }
            case KeyEvent.VK_D:
                if (position == CrossyRoadGame.GAME_WIDTH - GameBoard.PIXELS_PER_UNIT) {
                    return position;
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

    public String getCrossyRoadPlayerName() {
        return this.playerName;
    }

    public Color getPlayerColor() {
        return this.playerColor;
    }
}
