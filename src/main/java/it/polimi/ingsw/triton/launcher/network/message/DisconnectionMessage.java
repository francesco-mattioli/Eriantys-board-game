package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class DisconnectionMessage extends Message{
    private final String disconnectedNickname;
    public DisconnectionMessage(String disconnectedNickname) {
        super(Game.NAME_SERVER, MessageType.DISCONNECTION);
        this.disconnectedNickname = disconnectedNickname;
    }
}
