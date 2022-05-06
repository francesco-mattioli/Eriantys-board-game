package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message communicates to the players that an island has a new dominator.
 */
public class ChangeInfluenceMessage extends InfoMessage {
    private Island islandWithNewInfluence;
    private String usernameDominator;

    public ChangeInfluenceMessage(Island islandWithNewInfluence, String usernameDominator) {
        this.islandWithNewInfluence = islandWithNewInfluence;
        this.usernameDominator = usernameDominator;
    }

    public Island getIslandWithNewInfluence() {
        return islandWithNewInfluence;
    }

    public String getUsernameDominator() {
        return usernameDominator;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
