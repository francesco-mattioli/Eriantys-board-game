package it.polimi.ingsw.triton.launcher.utils.exceptions;

public class ChangeTurnException extends Exception{
    public ChangeTurnException() {
        super("The turn has been changed");
    }
}
