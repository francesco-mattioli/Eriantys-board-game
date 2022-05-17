package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to the players that 2 islands are merged and the new island
 * has a dominator.
 * The parameter islandWithMotherNature is one of the island to merge and this will not be deleted in client model.
 * On the other side, the parameter islandToDelete is the other merged island and this will be deleted in client model.
 */
public class MergeIslandsMessage extends InfoMessage {
    private final Island islandWithMotherNature;
    private final Island islandToDelete;
    public MergeIslandsMessage(Island islandwithMotherNature, Island islandToDelete) {
        this.islandWithMotherNature = islandwithMotherNature;
        this.islandToDelete = islandToDelete;
    }

    public Island getIslandWithMotherNature() {
        return islandWithMotherNature;
    }

    public Island getIslandToDelete() {
        return islandToDelete;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
