package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;

import java.util.ArrayList;

public class TowerColorRequest extends Message{
    private boolean [] availableTowerColors;
    private String receiverNickname;
    public TowerColorRequest(boolean [] availableTowerColors, String receiverNickname) {
        super(Game.NAME_SERVER, MessageType.TOWER_COLOR_REQUEST);
        this.availableTowerColors = availableTowerColors;
        this.receiverNickname = receiverNickname;
    }
}
