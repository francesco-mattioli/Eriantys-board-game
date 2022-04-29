package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the players that one player has moved his towers onto an island.
 */
public class MoveTowerOntoIslandMessage extends BroadcastServerMessage{
    private String usernameDominator;
    private Island island;
    public MoveTowerOntoIslandMessage(String usernameDominator, Island island) {
        super(MessageType.TOWER_ON_ISLAND);
        this.usernameDominator = usernameDominator;
        this.island = island;
    }

    public String getUsernameDominator() {
        return usernameDominator;
    }

    public Island getIsland() {
        return island;
    }
}
