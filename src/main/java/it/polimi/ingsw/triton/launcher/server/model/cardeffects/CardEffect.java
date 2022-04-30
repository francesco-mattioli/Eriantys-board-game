package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.utils.EndGameException;

public interface CardEffect {
    void execute() throws EndGameException;
}
