package model;

import javax.swing.Timer;


public class CrossyRoadRun {
    private CrossyRoadGame crossyRoadGame;
    public static final  int CONSOLE_INTERVAL = 1000;

    /*
     * REQUIRES: player is a valid PlayerProfile object
     * MODIFIES: this
     * EFFECTS: parent class that will run the entirety of the CrossyRoad game
     *          prints out the current player's name
     *          instantiates a new CrossyRoadGame object
     *          calls addTimer
     */
    public CrossyRoadRun(PlayerProfile player) {
        System.out.println(player.getPlayerName() + " is playing !\n");
        this.crossyRoadGame = new CrossyRoadGame(player);
        addTimer();
    }

    // This method was taken from the SpaceInvadersBase in-lecture lab code base
    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {

        Timer updateTimer = new Timer(CONSOLE_INTERVAL, ae -> crossyRoadGame.update());
        updateTimer.start();
    }

//    private class KeyHandler extends KeyAdapter {
//        @Override
//        public void keyPressed(KeyEvent e) {
//            crossyRoadGame.keyPressed(e.getKeyCode());
//        }
//    }
}
