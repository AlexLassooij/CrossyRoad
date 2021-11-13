package model;

import java.awt.*;

public enum Colors {
    RED(255, 20, 20),
    PINK(255, 20, 200),
    ORANGE(255, 100, 50),
    GREEN(128, 250, 0),
    TURQUOISE(20, 250, 250),
    YELLOW(250, 250, 58),
    BLUE(10, 50, 188),
    PURPLE(100, 0, 180)
    ;

    private final Color color;

    Colors(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    public static Color getRandomColor() {
        Colors randColor = values()[(int) (Math.random() * values().length)];
        return randColor.color;
    }
}
