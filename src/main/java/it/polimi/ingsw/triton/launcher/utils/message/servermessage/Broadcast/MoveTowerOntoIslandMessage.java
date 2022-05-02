package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the players that one player has moved his towers onto an island.
 */
public class MoveTowerOntoIslandMessage extends BroadcastServerMessage{
    private String usernameDominator;
    private Island island;
    public MoveTowerOntoIslandMessage(String usernameDominator, Island island) {
        this.usernameDominator = usernameDominator;
        this.island = island;
    }

    public String getUsernameDominator() {
        return usernameDominator;
    }

    public Island getIsland() {
        return island;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
