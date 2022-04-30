package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.utils.IllegalClientInputException;

public interface Action {
    void execute() throws IllegalClientInputException;
}
