package model;

import javax.swing.Timer;


public class CrossyRoadRun {
    private CrossyRoadGame crossyRoadGame;
    public static final  int CONSOLE_INTERVAL = 1000;
    private Timer updateTimer;
    private boolean isTimerRunning;

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
        this.isTimerRunning = false;
        addTimer();
    }

    // This method was taken from the SpaceInvadersBase in-lecture lab code base
    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {

        this.updateTimer = new Timer(CONSOLE_INTERVAL, ae -> crossyRoadGame.update());
        this.updateTimer.start();
        this.isTimerRunning = true;
    }

    public void stopTimer() {
        this.updateTimer.stop();
        this.isTimerRunning = false;
    }

    public boolean getTimerStatus() {
        return this.isTimerRunning;
    }

//    private class KeyHandler extends KeyAdapter {
//        @Override
//        public void keyPressed(KeyEvent e) {
//            crossyRoadGame.keyPressed(e.getKeyCode());
//        }
//    }
}
