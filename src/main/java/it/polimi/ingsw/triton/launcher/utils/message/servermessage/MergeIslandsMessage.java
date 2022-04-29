package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the player that 2 islands are merged and the new island has a dominator.
 */
public class MergeIslandsMessage extends BroadcastServerMessage{
    private final Island islandWithMotherNature;
    private final Island islandToDelete;
    private final String usernameDominator;
    public MergeIslandsMessage(String usernameDominator, Island islandwithMotherNature, Island islandToDelete) {
        super(MessageType.MERGE_ISLANDS);
        this.usernameDominator = usernameDominator;
        this.islandWithMotherNature = islandwithMotherNature;
        this.islandToDelete = islandToDelete;
    }

    public Island getIslandWithMotherNature() {
        return islandWithMotherNature;
    }

    public Island getIslandToDelete() {
        return islandToDelete;
    }

    public String getUsernameDominator() {
        return usernameDominator;
    }
}
