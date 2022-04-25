package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

import java.util.ArrayList;

public class TowerColorRequest extends ServerMessage {
    private final boolean [] availableTowerColors;
    private String receiverUsername;

    public TowerColorRequest(boolean [] availableTowerColors, String receiverUsername) {
        super(MessageType.TOWER_COLOR_REQUEST, receiverUsername);
        this.availableTowerColors = availableTowerColors;
    }

    public boolean[] getAvailableTowerColors() {
        return availableTowerColors;
    }
}
