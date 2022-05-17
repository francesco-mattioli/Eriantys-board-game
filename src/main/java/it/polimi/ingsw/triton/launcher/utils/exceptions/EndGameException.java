package it.polimi.ingsw.triton.launcher.utils.exceptions;

/**
 * This exception is thrown when the game reaches some conditions:
 * 1. a player builds his last tower onto an island (instantly);
 * 2. there are only three remaining groups of islands (instantly);
 * 3. a player draws the last student into the bag or ends his assistant deck (at the end of this round).
 * In these cases, this exception allows the server to call the method calculateWinner to establish the winner.
 */
public class EndGameException extends Exception{
    public EndGameException() {
        super("The game must finish!");
    }
}
