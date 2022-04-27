package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

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
