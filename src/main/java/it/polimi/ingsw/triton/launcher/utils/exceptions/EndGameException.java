package it.polimi.ingsw.triton.launcher.utils.exceptions;

public class EndGameException extends Exception{
    public EndGameException() {
        super("The game must finish!");
    }
}
