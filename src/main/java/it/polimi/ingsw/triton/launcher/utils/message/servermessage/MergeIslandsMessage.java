package it.polimi.ingsw.triton.launcher.utils.message.servermessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;

/**
 * This message communicates to the player that 2 islands are merged and the new island has a dominator.
 */
public class MergeIslandsMessage extends BroadcastServerMessage {
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

    }
}
