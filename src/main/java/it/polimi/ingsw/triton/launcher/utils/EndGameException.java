package it.polimi.ingsw.triton.launcher.utils;

public class EndGameException extends Exception{
    public EndGameException() {
        super("The game must finish!");
    }
}
