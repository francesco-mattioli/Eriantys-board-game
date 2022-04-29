package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class TowerColorRequest extends ServerMessage {
    private final boolean [] chosenTowerColors;
    private String receiverUsername;

    public TowerColorRequest(boolean [] chosenTowerColors, String receiverUsername) {
        super(MessageType.TOWER_COLOR_REQUEST, receiverUsername);
        this.chosenTowerColors = chosenTowerColors;
    }

    public boolean[] getAvailableTowerColors() {
        return chosenTowerColors;
    }
}
