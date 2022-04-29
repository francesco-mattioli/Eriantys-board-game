package it.polimi.ingsw.triton.launcher.utils;

public class IllegalClientInputException extends Exception{
    public IllegalClientInputException() {
            super("Client has entered a wrong input!");
    }
}

