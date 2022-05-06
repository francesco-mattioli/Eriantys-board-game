package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message communicates to the players that one player has moved his towers onto an island.
 */
public class MoveTowerOntoIslandMessage extends InfoMessage {
    private final Island island;
    public MoveTowerOntoIslandMessage(Island island) {
        this.island = island;
    }

    public Island getIsland() {
        return island;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
