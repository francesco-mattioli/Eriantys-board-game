package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class GenericMessage extends Message{
    private String message;
    public GenericMessage(String message) {
        super(Game.NAME_SERVER, MessageType.GENERIC);
        this.message = message;
    }
}
