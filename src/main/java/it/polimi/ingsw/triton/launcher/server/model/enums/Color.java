package it.polimi.ingsw.triton.launcher.server.model.enums;

import java.io.Serializable;

/**
 * This enum contains the colors of students and professors of the game.
 */
public enum Color implements Serializable {
    YELLOW,
    BLUE,
    GREEN,
    RED,
    PINK;

    public static int numOfColors(){
        return Color.values().length;
    }
}
