package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

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
