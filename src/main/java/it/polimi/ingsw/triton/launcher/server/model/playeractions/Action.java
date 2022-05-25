package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import java.io.Serializable;

/**
 * Represents the action that a player can do during the game.
 */
public interface Action extends Serializable {
    void execute() throws IllegalClientInputException, EmptyGeneralCoinSupplyException;
}
