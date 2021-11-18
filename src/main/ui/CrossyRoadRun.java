package ui;

import model.CrossyRoadGame;
import model.PlayerProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrossyRoadRun extends JPanel implements ActionListener {
    private final CrossyRoadGame crossyRoadGame;
    private final GameBoard gameBoard;
    private final JScrollPane scrollPane;
    private final PlayerProfile player;
    private final Font arcadeFont = new Font("Arial",Font.BOLD, 20);
    public static final  int MOVE_INTERVAL = 5;
    private Timer updateTimer;

    /*
     * REQUIRES: player is a valid PlayerProfile object
     * MODIFIES: this
     * EFFECTS: parent class that will run the entirety of the CrossyRoad game
     *          prints out the current player's name
     *          instantiates a new CrossyRoadGame object
     *          calls addTimer
     */
    public CrossyRoadRun(PlayerProfile player)  {
//        ArcadeApp.addCentredString("Would you like to restart at level 1 or continue where you left off ?",
//                ArcadeApp.COMPONENT_HEIGHT, this, arcadeFont, getFontMetrics(arcadeFont));
//        add(ArcadeApp.createButton(ArcadeApp.BUTTON_POS_X, ArcadeApp.COMPONENT_HEIGHT * 2,
//                "Restart", "restartLevel"));
//        add(ArcadeApp.createButton(ArcadeApp.BUTTON_POS_X, ArcadeApp.COMPONENT_HEIGHT * 2,
//                "Continue", "continueLevel"));
//        String choice = "1";
//        if (player.getLevelAchieved() > 1) {
//            System.out.println("Would you like to start at level 1 (1) or "
//                    + "continue where you left off (2) ?");
//            choice = this.scanner.next();
//        }
//        boolean restartLevel = choice.equals("1");
        this.player = player;
        this.crossyRoadGame = new CrossyRoadGame(this.player, true);
        this.gameBoard = new GameBoard(this.crossyRoadGame);
        this.scrollPane = new CrossyRoadScrollPane(this.gameBoard);
        add(scrollPane);
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
                setScrollBarToTop();
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
        if (this.gameBoard.getPreferredSize().getHeight() < this.crossyRoadGame.getGameHeight()) {
            this.gameBoard.setPreferredSize(new Dimension(CrossyRoadGame.GAME_WIDTH,
                    this.crossyRoadGame.getGameHeight()));
            this.gameBoard.revalidate();
            System.out.println("Resetting gameheight to " + this.crossyRoadGame.getGameHeight());
        }
        this.gameBoard.repaint();
        if (this.crossyRoadGame.getGameStatus().equals("ONGOING")) {
            setScrollBar();
        }
    }

    private void setScrollBar() {
        if (!this.crossyRoadGame.getGameStatus().equals("ONGOING")) {
            setScrollBarToTop();
        } else {
            this.scrollPane.getVerticalScrollBar().setValue(this.scrollPane.getVerticalScrollBar().getMaximum()
                    - this.crossyRoadGame.getCrossyRoadPlayer().getPositionY() - 400);
        }


    }

    private void setScrollBarToTop() {
        this.scrollPane.getVerticalScrollBar().setValue(0);
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
}
