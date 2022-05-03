package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

import java.io.Serializable;

public interface Action extends Serializable {
    void execute() throws IllegalClientInputException;
}
