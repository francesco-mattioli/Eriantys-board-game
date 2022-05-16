package it.polimi.ingsw.triton.launcher.server.model.enums;

import java.io.Serializable;

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
