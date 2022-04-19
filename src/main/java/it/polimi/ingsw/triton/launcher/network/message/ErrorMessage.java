package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class ErrorMessage extends Message{
    private String error;
    public ErrorMessage(String error) {
        super(Game.NAME_SERVER, MessageType.ERROR);
        this.error = error;
    }
}
