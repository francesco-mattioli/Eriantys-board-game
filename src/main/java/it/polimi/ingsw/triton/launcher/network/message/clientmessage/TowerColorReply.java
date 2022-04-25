package it.polimi.ingsw.triton.launcher.network.message.clientmessage;

import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class TowerColorReply extends ClientMessage {
    private final TowerColor playerColor;

    public TowerColorReply(String username, TowerColor playerColor) {
        super(MessageType.TOWER_COLOR_REPLY, username);
        this.playerColor = playerColor;
    }
    public TowerColor getPlayerColor() {
        return playerColor;
    }

}
