package it.polimi.ingsw.triton.launcher.utils;

public class LastMoveException extends Exception{
    public LastMoveException() {
        super("The move done was the last one");
    }
}
