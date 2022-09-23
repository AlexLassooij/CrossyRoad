package ui;

import model.Colors;
import model.CrossyRoadCar;
import model.CrossyRoadGame;
import model.CrossyRoadPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameBoard extends JPanel {
    public static final int PIXELS_PER_UNIT = 100;
    public static final int NORMAL_PLAYER_POS = CrossyRoadGame.INIT_HEIGHT / 200 * 100;
    public static final int OFFSET_FROM_PLAYER = 300;
    public static final int TEXT_LEVEL_ONE = 50;
    public static final int TEXT_LEVEL_TWO = 150;
    public static final int TEXT_LEVEL_THREE = 250;
    public static final int GAME_OBJECT_PADDING = 10;
    private static final String CHICKEN_PATH = "./data/images/chicken.png";
    private BufferedImage chicken;
    private static final String COLLISION = "Game over ! You have collided with a car !";
    private static final String COMPLETION = "Congratulations ! You have completed the level !";
    private static final String CONTINUE = "Press 'C' to continue to the next level";
    private static final String RESTART = "Press 'R' to restart";
    private static final String QUIT = "Press 'Q' to quit";
    private final CrossyRoadGame game;
    private static GameBoard theBoard;

    /*
     * MODIFIES: this
     * EFFECTS: sets the height and width of the gameBoard
     *          instantiates a new 2D String array
     */
    public GameBoard() {
        this.game = CrossyRoadGame.getInstance();
        setPanelSize();
        setOpaque(false);
        try {
            chicken = ImageIO.read(new File(CHICKEN_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameBoard getInstance() {
        if (theBoard == null) {
            theBoard = new GameBoard();
        }
        return theBoard;
    }

    public void setPanelSize() {
        Dimension size = new Dimension(CrossyRoadGame.GAME_WIDTH, CrossyRoadGame.INIT_HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
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
        //paintBackground(g);
        paintCars(g, cars, player);
        paintPlayer(g, player);
    }

    private void paintCars(Graphics g, List<CrossyRoadCar> cars, CrossyRoadPlayer player) {
        // the y coordinate to paint the car on the board, which is not the car's actual position
        for (CrossyRoadCar nextCar : cars) {
            paintCar(g, nextCar, DisplayController.getInstance().getCarIndex() - nextCar.getCarPositionY());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: prints a coloured "o" for each position that the car
     *          occupies on the gameBoard
     */
    private void paintCar(Graphics g, CrossyRoadCar car, int carPositionY) {
        int carPositionX = car.getCarPositionX();
        int carLength = car.getCarLength();
        String movementDirection = car.getMovementDirection();
        Color previousColor = g.getColor();
        g.setColor(car.getCarColour());
        if (movementDirection.equals("right")) {
            g.fillRect(carPositionX - carLength * PIXELS_PER_UNIT + GAME_OBJECT_PADDING,
                    carPositionY + GAME_OBJECT_PADDING, carLength * PIXELS_PER_UNIT - 2 * GAME_OBJECT_PADDING,
                    PIXELS_PER_UNIT - 2 * GAME_OBJECT_PADDING);
        } else {
            g.fillRect(carPositionX + GAME_OBJECT_PADDING,
                    carPositionY + GAME_OBJECT_PADDING, carLength * PIXELS_PER_UNIT - 2 * GAME_OBJECT_PADDING,
                    PIXELS_PER_UNIT - 2 * GAME_OBJECT_PADDING);
        }
        g.setColor(previousColor);
    }

    /*
     * MODIFIES: this
     * EFFECTS: prints an "x" on the position that the player
     *          occupies on the gameBoard
     */
    private void paintPlayer(Graphics g, CrossyRoadPlayer player) {
        Color previousColor = g.getColor();
        g.setColor(player.getPlayerColor());
        int positionX = player.getPositionX();
//        g.fillRect(positionX + GAME_OBJECT_PADDING, DisplayController.getInstance().getPlayerIndex()
//                        + GAME_OBJECT_PADDING,
//                PIXELS_PER_UNIT - 2 * GAME_OBJECT_PADDING, PIXELS_PER_UNIT - 2 * GAME_OBJECT_PADDING);
        
        g.drawImage(chicken,positionX + GAME_OBJECT_PADDING, DisplayController.getInstance().getPlayerIndex()
                        + GAME_OBJECT_PADDING,
                PIXELS_PER_UNIT - 2 * GAME_OBJECT_PADDING, PIXELS_PER_UNIT - 2 * GAME_OBJECT_PADDING, null);

        g.setColor(previousColor);
    }

    private void displayFailure(Graphics g) {
        paintGameBoard(g);
        Color saved = g.getColor();
        g.setColor(Colors.TURQUOISE.color);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(COLLISION, g, fm, TEXT_LEVEL_ONE);
        centreString(RESTART, g, fm, TEXT_LEVEL_TWO);
        centreString(QUIT, g, fm, TEXT_LEVEL_THREE);
        g.setColor(saved);
    }

    private void displayCompletion(Graphics g) {
        paintGameBoard(g);
        Color saved = g.getColor();
        g.setColor(Colors.TURQUOISE.color);
        g.setFont(new Font("Arial",Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(COMPLETION, g, fm, TEXT_LEVEL_ONE);
        centreString(CONTINUE, g, fm, TEXT_LEVEL_TWO);
        centreString(QUIT, g, fm, TEXT_LEVEL_THREE);
        g.setColor(saved);
    }

    // old section of code that used to be in paintCars
    // case 1 : player is neither near top or bottom of game boundary and is being painted in center of
//            // screen regardless of position
//            if (playerPositionY >= NORMAL_PLAYER_POS && playerPositionY < game.getGameHeight() - NORMAL_PLAYER_POS) {
//                if ((playerPositionY - OFFSET_FROM_PLAYER) <= nextCar.getCarPositionY()
//                        && nextCar.getCarPositionY() <= (playerPositionY + OFFSET_FROM_PLAYER)) {
//                    int carPositionY =
//                            NORMAL_PLAYER_POS + playerPositionY - nextCar.getCarPositionY();
//                    paintCar(g, nextCar, DisplayController.getInstance().getCarIndex());
//                }
//            // case 2 : if player is near bottom of screen, display cars from player's level until top of screen
//            } else if (playerPositionY < NORMAL_PLAYER_POS) {
//                if (nextCar.getCarPositionY() < CrossyRoadGame.INIT_HEIGHT) {
//                    int carPositionY = game.getGameHeight()  - PIXELS_PER_UNIT
//                            - (game.getGameHeight() - CrossyRoadGame.INIT_HEIGHT) - nextCar.getCarPositionY();
//                    paintCar(g, nextCar, DisplayController.getInstance().getCarIndex());
//                }
//            // case 3:  player is near top,
//            } else {
//                if (nextCar.getCarPositionY() >= game.getGameHeight() - CrossyRoadGame.INIT_HEIGHT) {
//                    int carPositionY = game.getGameHeight() - PIXELS_PER_UNIT - nextCar.getCarPositionY();
//                    paintCar(g, nextCar, DisplayController.getInstance().getCarIndex());
//                }
//            }
}
