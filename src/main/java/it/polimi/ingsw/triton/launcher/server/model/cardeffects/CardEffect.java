package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;

public interface CardEffect {
    void execute() throws EndGameException;
}
