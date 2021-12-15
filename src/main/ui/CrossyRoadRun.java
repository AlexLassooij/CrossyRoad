package ui;

import model.CrossyRoadGame;
import model.PlayerProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrossyRoadRun extends JPanel implements ActionListener {
    private final CrossyRoadGame crossyRoadGame;
    private final BackGroundPanel background;
    private final GameBoard gameBoard;
    // private final JScrollPane scrollPane;
    private final PlayerProfile player;
    private CrossyRoadEventHandler eventHandler;
    public static final int MOVE_INTERVAL = 50;
    public static final int MOVE_MULTIPLIER = MOVE_INTERVAL / 5;
    private Timer updateTimer;

    /*
     * REQUIRES: player is a valid PlayerProfile object
     * MODIFIES: this
     * EFFECTS: parent class that will run the entirety of the CrossyRoad game
     *          prints out the current player's name
     *          instantiates a new CrossyRoadGame object
     *          calls addTimer
     */
    public CrossyRoadRun(PlayerProfile player, boolean restartFlag)  {
        this.player = player;
        this.crossyRoadGame = new CrossyRoadGame(this.player, restartFlag);
        this.background = new BackGroundPanel();
        this.gameBoard = new GameBoard();
        setPreferredSize(new Dimension(CrossyRoadGame.GAME_WIDTH, this.crossyRoadGame.getGameHeight()));
        JLayeredPane mainGamePanel = new JLayeredPane();
        mainGamePanel.setPreferredSize(new Dimension(CrossyRoadGame.GAME_WIDTH, this.crossyRoadGame.getGameHeight()));
        mainGamePanel.add(background, 0);
        mainGamePanel.add(gameBoard, 0);
        // this.scrollPane = new CrossyRoadScrollPane(mainGamePanel);
        add(mainGamePanel);
        this.eventHandler = new CrossyRoadEventHandler();
        setVisible(true);
        addTimer();
    }

    // This method was taken from the SpaceInvadersBase in-lecture lab code base
    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        this.updateTimer = new Timer(MOVE_INTERVAL, ae -> {
            if (this.crossyRoadGame.getGameStatus().equals("QUIT")) {
                stopTimer();
                // setScrollBarToTop();
                System.out.println("game quit");
            }
            update();
        });
        this.updateTimer.start();
    }

    public void stopTimer() {
        this.updateTimer.stop();
    }


    /*
     * MODIFIES: this
     * EFFECTS: calls handleInput to request the input for the next player move
     *          checks if the player has completed the game. if so, sets the gameStatus
     *          to "COMPLETED" and calls handleGameCompletion
     *          updates the gameBoard since the player and the cars will have moved
     */
    private void update() {
        this.crossyRoadGame.moveCars();
        this.gameBoard.repaint();
    }

    public CrossyRoadGame getCrossyRoadGame() {
        return this.crossyRoadGame;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        JButton button = (JButton)ae.getSource();
        String label = button.getText();
        switch (action) {
            case "restartLevel":
                this.crossyRoadGame.setCurrentLevel(1);
                break;
            case "continueLevel":
                this.crossyRoadGame.setCurrentLevel(this.player.getLevelAchieved());
                break;
        }
    }

    public CrossyRoadEventHandler getEventHandler() {
        return this.eventHandler;
    }
}
