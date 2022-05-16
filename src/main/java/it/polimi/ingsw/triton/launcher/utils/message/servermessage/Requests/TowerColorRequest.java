package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;

/**
 * This message is sent from the server to ask a player which tower color wants
 * in the game.
 * The parameter chosenTowerColors contains all the colors of the game and if they are free or already used.
 */
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
