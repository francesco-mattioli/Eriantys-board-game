package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the player that 2 islands are merged and the new island has a dominator.
 */
public class MergeIslandsMessage extends BroadcastServerMessage{
    private final Island island1;
    private final Island island2;
    private final String usernameDominator;
    public MergeIslandsMessage(String usernameDominator, Island island1, Island island2) {
        super(MessageType.MERGE_ISLANDS);
        this.usernameDominator = usernameDominator;
        this.island1 = island1;
        this.island2 = island2;
    }

    public Island getIsland1() {
        return island1;
    }

    public Island getIsland2() {
        return island2;
    }

    public String getUsernameDominator() {
        return usernameDominator;
    }
}
