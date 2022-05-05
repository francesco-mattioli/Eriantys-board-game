package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;

public class TowerColorRequest extends AskMessage {
    private final boolean [] chosenTowerColors;

    public TowerColorRequest(boolean [] chosenTowerColors) {
        this.chosenTowerColors = chosenTowerColors;
    }

    public boolean[] getChosenTowerColors() {
        return chosenTowerColors;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
