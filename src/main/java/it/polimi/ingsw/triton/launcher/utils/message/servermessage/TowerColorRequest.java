package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class TowerColorRequest extends ServerMessage {
    private final boolean [] chosenTowerColors;

    public TowerColorRequest(boolean [] chosenTowerColors) {
        super(MessageType.TOWER_COLOR_REQUEST);
        this.chosenTowerColors = chosenTowerColors;
    }

    public boolean[] getAvailableTowerColors() {
        return chosenTowerColors;
    }
}
