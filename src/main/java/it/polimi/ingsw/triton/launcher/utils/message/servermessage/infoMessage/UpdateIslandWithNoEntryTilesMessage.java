package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to all the players
 * the island with a new number of no entry tiles on it.
 * The parameter islandToUpdate is the updated island.
 */
public class UpdateIslandWithNoEntryTilesMessage extends InfoMessage {
    private final Island islandToUpdate;

    public UpdateIslandWithNoEntryTilesMessage(Island islandToUpdate){
        this.islandToUpdate = islandToUpdate;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public Island getIslandToUpdate() {
        return islandToUpdate;
    }
}
