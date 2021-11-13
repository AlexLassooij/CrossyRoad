package ui;

import model.CrossyRoadGame;

import javax.swing.*;
import java.awt.*;

public class CrossyRoadScrollPane extends JScrollPane {

    public CrossyRoadScrollPane(JPanel gameBoard) {
        setViewportView(gameBoard);
        setPreferredSize(new Dimension(CrossyRoadGame.GAME_WIDTH, GameBoard.INIT_HEIGHT));
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setViewportBorder(BorderFactory.createLineBorder(Color.ORANGE));
    }
}
