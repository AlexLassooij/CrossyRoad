package ui;

import model.CrossyRoadGame;

public class DisplayController {
    private int backGroundIndex;
    private int carIndex;
    private int playerIndex;
    private static DisplayController theDisplay;

    public DisplayController() {
    }

    public static DisplayController getInstance() {
        if (theDisplay == null) {
            theDisplay = new DisplayController();
        }
        return theDisplay;
    }

    /*
     * MODIFIES: this
     * EFFECTS: depending on the location of the player within the CrossyRoadGame,
     * modifies indices corresponding to the display of the player, cars and background
     * in order to have the game "follow" the player as they move throughout the game
     */
    public void updateDisplayIndices() {
        int playerPositionY = CrossyRoadGame.getInstance().getCrossyRoadPlayer().getPositionY();
        int gameHeight = CrossyRoadGame.getInstance().getGameHeight();
        // case 1 : player is neither near top or bottom of game boundary and is being painted in center of
        // screen regardless of position
        if (playerPositionY >= GameBoard.NORMAL_PLAYER_POS && playerPositionY < gameHeight
                - GameBoard.NORMAL_PLAYER_POS) {
            backGroundIndex = playerPositionY - GameBoard.NORMAL_PLAYER_POS;
            carIndex = GameBoard.NORMAL_PLAYER_POS + playerPositionY;
            playerIndex = GameBoard.NORMAL_PLAYER_POS;
        // case 2 : if player is near bottom of screen, display cars from player's level until top of screen
        } else if (playerPositionY < GameBoard.NORMAL_PLAYER_POS) {
            backGroundIndex = 0;
            carIndex = CrossyRoadGame.INIT_HEIGHT - GameBoard.PIXELS_PER_UNIT;
            playerIndex = CrossyRoadGame.INIT_HEIGHT - playerPositionY - GameBoard.PIXELS_PER_UNIT;
        // case 3:  player is near top,
        } else {
            backGroundIndex = gameHeight - CrossyRoadGame.INIT_HEIGHT;
            carIndex = gameHeight - GameBoard.PIXELS_PER_UNIT;
            playerIndex = gameHeight - playerPositionY - GameBoard.PIXELS_PER_UNIT;
        }
    }

    public int getBackGroundIndex() {
        return backGroundIndex;
    }

    public int getCarIndex() {
        return carIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
