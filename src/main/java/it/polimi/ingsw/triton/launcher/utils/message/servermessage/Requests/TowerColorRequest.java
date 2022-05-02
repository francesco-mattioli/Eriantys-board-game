package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public class TowerColorRequest extends ServerMessage {
    private final boolean [] chosenTowerColors;

    public TowerColorRequest(boolean [] chosenTowerColors) {
        this.chosenTowerColors = chosenTowerColors;
    }

    public boolean[] getAvailableTowerColors() {
        return chosenTowerColors;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        
    }
}
