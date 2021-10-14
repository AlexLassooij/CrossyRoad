package model;

import java.awt.event.KeyEvent;

public class CrossyRoadPlayer {
    private int positionX;
    private int positionY;
    private String playerName;

    public CrossyRoadPlayer(String playerName) {
        this.positionX = CrossyRoadGame.GAME_WIDTH / 2;
        this.positionY = 0;
        this.playerName = playerName;
    }

    /*
     * MODIFIES: this
     * EFFECTS: moves the player up, down, left or right by one unit according to "directionOfMovement"
     */
    public void movePlayer(String directionOfMovement) {
        if (directionOfMovement.equals("w") || directionOfMovement.equals("s")) {
            this.positionY = checkBoundariesAndMove(directionOfMovement, this.positionY);
        } else {
            this.positionX = checkBoundariesAndMove(directionOfMovement, this.positionX);
        }
        System.out.println("Your position: X: " + this.positionX + " Y: " + this.positionY);
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
    public int checkBoundariesAndMove(String directionOfMovement, int position) {
        switch (directionOfMovement) {
            case "w":
                return position + 1;
            case "s":
            case "a":
                if (position == 0) {
                    return position;
                } else {
                    return position - 1;
                }
            case "d":
                if (position == CrossyRoadGame.GAME_WIDTH - 1) {
                    return position;
                } else {
                    return position + 1;
                }
            default:
                System.out.println("Invalid key Press");
                return position;
        }
    }

    // requires the main game to check for a game win
    private int checkBoundariesAndMove(int keyCode, int position) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                return position + 1;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
                if (position == 0) {
                    return position;
                } else {
                    return position - 1;
                }
            case KeyEvent.VK_RIGHT:
                if (position == CrossyRoadGame.GAME_WIDTH - 1) {
                    return position;
                } else {
                    return position + 1;
                }
            default:
                System.out.println("Invalid key Press");
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
}
