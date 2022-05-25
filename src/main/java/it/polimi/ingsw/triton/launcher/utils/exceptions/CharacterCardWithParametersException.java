package it.polimi.ingsw.triton.launcher.utils.exceptions;

/**
 * This exception is thrown when a character card selected by the player needs some
 * parameters to build his effect.
 * When this exception is thrown, the server asks the current player to insert the parameters.
 */
public class CharacterCardWithParametersException extends Exception {
    public CharacterCardWithParametersException() {
        super("This character card needs some parameters");
    }
}
