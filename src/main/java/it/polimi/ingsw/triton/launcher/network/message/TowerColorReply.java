package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;

public class TowerColorReply extends Message{
    private TowerColor playerColor;
    public TowerColorReply(String nickname, TowerColor playerColor) {
        super(nickname, MessageType.TOWER_COLOR_REPLY);
        this.playerColor = playerColor;
    }
}
