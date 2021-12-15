package ui;

import model.CrossyRoadGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackGroundPanel extends JPanel {
    private static final String GRASS_PATH = "./data/images/grass_flowers.jpeg";
    private static final String ROAD_PATH = "./data/images/road.png";
    private BufferedImage grass;
    private BufferedImage road;
    public static final int PIXELS_PER_UNIT = 100;
    private final CrossyRoadGame game;
    private static BackGroundPanel theBackground;


    public BackGroundPanel() {
        this.game = CrossyRoadGame.getInstance();
        setPanelSize();

        try {
            grass = ImageIO.read(new File(GRASS_PATH));
            road = ImageIO.read(new File(ROAD_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BackGroundPanel getInstance() {
        if (theBackground == null) {
            theBackground = new BackGroundPanel();
        }
        return theBackground;
    }

    public void setPanelSize() {
        Dimension size = new Dimension(CrossyRoadGame.GAME_WIDTH, CrossyRoadGame.INIT_HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
    }

    public void paintComponent(Graphics g) {
        paintBackground(g);
    }

    private void paintBackground(Graphics g) {
        BufferedImage image;
        for (int i = 0; i < CrossyRoadGame.INIT_HEIGHT; i += PIXELS_PER_UNIT) {
            // starting Y position to start drawing from,
            // drawing start from top left corner,
            // gameHeight corresponds to bottom of frame
            int paintedPositionY = CrossyRoadGame.INIT_HEIGHT - i - PIXELS_PER_UNIT;
            if (game.getRoadCoordinates().contains(i + DisplayController.getInstance().getBackGroundIndex())) {
                image = road;
            } else {
                image = grass;
            }
            for (int j = 0; j < CrossyRoadGame.GAME_WIDTH; j++) {
                g.drawImage(image, j, paintedPositionY, PIXELS_PER_UNIT, PIXELS_PER_UNIT, null);
            }
        }
    }

}
