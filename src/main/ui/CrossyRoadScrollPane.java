package ui;

import model.CrossyRoadGame;

import javax.swing.*;
import java.awt.*;

public class CrossyRoadScrollPane extends JScrollPane {

    public CrossyRoadScrollPane(JLayeredPane pane) {
        setViewportView(pane);
        setPreferredSize(new Dimension(CrossyRoadGame.GAME_WIDTH, CrossyRoadGame.getInstance().getGameHeight()));
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        setViewportBorder(BorderFactory.createLineBorder(Color.ORANGE));
    }
}
