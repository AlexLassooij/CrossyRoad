package ui;

import model.CrossyRoadCar;
import model.CrossyRoadGame;
import model.CrossyRoadPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameBoard extends JPanel {
    public static final int PIXELS_PER_UNIT = 100;
    public static final int INIT_HEIGHT = 700;
    public static final int TEXT_LEVEL_ONE = 50;
    public static final int TEXT_LEVEL_TWO = 150;
    public static final int TEXT_LEVEL_THREE = 250;
    private static final String COLLISION = "Game over ! You have collided with a car !";
    private static final String COMPLETION = "Congratulations ! You have completed the level !";
    private static final String CONTINUE = "Press 'C' to continue to the next level";
    private static final String RESTART = "Press 'R' to restart";
    private static final String QUIT = "Press 'Q' to quit";
    private final CrossyRoadGame game;

    /*
     * MODIFIES: this
     * EFFECTS: sets the height and width of the gameBoard
     *          instantiates a new 2D String array
     */
    public GameBoard(CrossyRoadGame game) {
        this.game = game;
        int gameWidth = CrossyRoadGame.GAME_WIDTH;
        setPreferredSize(new Dimension(gameWidth, INIT_HEIGHT));
        System.out.println((this.game.getGameHeight()));
        setBackground(Color.PINK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (this.game.getGameStatus()) {
            case "ONGOING":
                paintGameBoard(g);
                break;
            case "FAILED":
                displayFailure(g);
                break;
            case "COMPLETED":
                displayCompletion(g);
                break;
        }
    }

    private void displayFailure(Graphics g) {
        paintGameBoard(g);
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(COLLISION, g, fm, TEXT_LEVEL_ONE);
        centreString(RESTART, g, fm, TEXT_LEVEL_TWO);
        centreString(QUIT, g, fm, TEXT_LEVEL_THREE);
        g.setColor(saved);
    }

    private void displayCompletion(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial",Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(COMPLETION, g, fm, TEXT_LEVEL_ONE);
        centreString(CONTINUE, g, fm, TEXT_LEVEL_TWO);
        centreString(QUIT, g, fm, TEXT_LEVEL_THREE);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string horizontally onto g at vertical position yPos
    private void centreString(String string, Graphics g, FontMetrics fm, int positionY) {
        int width = fm.stringWidth(string);
        g.drawString(string, (CrossyRoadGame.GAME_WIDTH - width) / 2, positionY);
    }

    /*
     * REQUIRES: height and width and both > 0
     * MODIFIES: this
     * EFFECTS: prints the gameBoard containing all cars and the player in the standard output
     */

    /*
     * MODIFIES: this
     * EFFECTS: populates the gameBoard with each car and the current player
     *          does not print the player if they completed the level, since
     *          the player will be out of bounds
     */
    private void paintGameBoard(Graphics g) {
        List<CrossyRoadCar> cars = this.game.getCars();
        CrossyRoadPlayer player = this.game.getCrossyRoadPlayer();
        drawCars(g, cars);
        paintPlayer(g, player);
    }

    private void drawCars(Graphics g, List<CrossyRoadCar> cars) {
        for (CrossyRoadCar nextCar : cars) {
            paintCar(g, nextCar);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: prints a coloured "o" for each position that the car
     *          occupies on the gameBoard
     */
    private void paintCar(Graphics g, CrossyRoadCar car) {
        int carPositionX = car.getCarPositionX();
//        System.out.println(positionX);
        int carPositionY = game.getGameHeight() - car.getCarPositionY();
        int carLength = car.getCarLength();
        String movementDirection = car.getMovementDirection();
        Color previousColor = g.getColor();
        g.setColor(car.getCarColour());
        if (movementDirection.equals("right")) {
            g.fillRect(carPositionX - (carLength - 1) * PIXELS_PER_UNIT,
                    carPositionY - PIXELS_PER_UNIT, carLength * PIXELS_PER_UNIT,
                    PIXELS_PER_UNIT);
        } else {
            g.fillRect(carPositionX,
                    carPositionY - PIXELS_PER_UNIT, carLength * PIXELS_PER_UNIT,
                    PIXELS_PER_UNIT);
        }
        g.setColor(previousColor);
    }


//    for (int i = 0; i < carLength * PIXELS_PER_UNIT; i += PIXELS_PER_UNIT) {
//        if (movementDirection.equals("right") && (positionX - i) < gameWidth && (positionX - i) >= 0) {
//            g.fillRect(positionX, positionY - PIXELS_PER_UNIT, PIXELS_PER_UNIT, PIXELS_PER_UNIT);
//        } else if (movementDirection.equals("left") && (positionX + i) < gameWidth && (positionX + i) >= 0) {
//            g.fillRect(positionX, positionY - PIXELS_PER_UNIT, PIXELS_PER_UNIT, PIXELS_PER_UNIT);
//        }
//    }
    /*
     * MODIFIES: this
     * EFFECTS: prints an "x" on the position that the player
     *          occupies on the gameBoard
     */
    private void paintPlayer(Graphics g, CrossyRoadPlayer player) {
        Color previousColor = g.getColor();
        g.setColor(player.getPlayerColor());
        int positionX = player.getPositionX();
        int positionY = game.getGameHeight() - player.getPositionY();
        g.fillRect(positionX, positionY - PIXELS_PER_UNIT, PIXELS_PER_UNIT, PIXELS_PER_UNIT);
        g.setColor(previousColor);
    }
}
