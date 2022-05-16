package it.polimi.ingsw.triton.launcher.utils.exceptions;

/**
 * This exception is thrown when a player ends his round.
 */
public class ChangeTurnException extends Exception{
    public ChangeTurnException() {
        super("The turn has been changed");
    }
}
