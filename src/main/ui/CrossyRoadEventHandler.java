package ui;

import exceptions.InvalidKeyPressException;
import exceptions.QuitGameException;
import model.CrossyRoadGame;

import java.awt.event.KeyEvent;

public class CrossyRoadEventHandler {
    private final CrossyRoadGame game;

    public CrossyRoadEventHandler(CrossyRoadGame game) {
        this.game = game;
    }

    public void handleKeyPress(int keyCode) throws QuitGameException {
        String gameStatus = this.game.getGameStatus();
        try {
            switch (gameStatus) {
                case "FAILED":
                    handleFailedGame(keyCode);
                    break;
                case "COMPLETED":
                    handleCompletedGame(keyCode);
                    break;
                case "ONGOING":
                    handleMoveInput(keyCode);
            }
        } catch (InvalidKeyPressException e) {
            handleKeyPress(keyCode);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: tells the user that they have completed the level
     *          asks whether they would like to continue to the next level
     *          if yes : increments the game's current level and the current player's
     *          highest achieved level
     *          calls configureDifficulty and setUpCrossyRoad to set up and set the difficulty
     *          for the next level
     *          if no : quits the program
     */
    private void handleCompletedGame(int keyCode) throws InvalidKeyPressException, QuitGameException {
        this.game.getArcadePlayer().increaseLevelAchieved();
        switch (keyCode) {
            case KeyEvent.VK_C:
                this.game.increaseLevel();
                this.game.configureDifficulty();
                this.game.setUpCrossyRoad();
                break;
            case KeyEvent.VK_Q:
                this.game.setGameStatus("QUIT");
                this.game.clearCars();
                throw new QuitGameException();
            default:
                throw new InvalidKeyPressException();
        }
    }

    private void handleFailedGame(int keyCode) throws InvalidKeyPressException, QuitGameException {
        switch (keyCode) {
            case KeyEvent.VK_R:
                this.game.setGameStatus("RESTARTING");
                this.game.setUpCrossyRoad();
                break;
            case KeyEvent.VK_Q:
                this.game.setGameStatus("QUIT");
                throw new QuitGameException();
            default:
                throw new InvalidKeyPressException();
        }
    }

    private void handleMoveInput(int keyCode) {
        System.out.println("handling move");
        this.game.getCrossyRoadPlayer().movePlayer(keyCode);
        if (this.game.checkCompletion()) {
            this.game.setGameStatus("COMPLETED");
        }
    }
}
