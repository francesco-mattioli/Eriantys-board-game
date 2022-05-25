package it.polimi.ingsw.triton.launcher.utils.exceptions;

/**
 * This exception is thrown when the player doesn't have to do other moves.
 */
public class LastMoveException extends Exception {
    public LastMoveException() {
        super("The move done was the last one");
    }
}
