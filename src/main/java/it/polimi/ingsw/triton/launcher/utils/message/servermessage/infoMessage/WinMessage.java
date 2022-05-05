package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.BroadcastMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message communicates the win to the winner player.
 */
public class WinMessage extends InfoMessage {
    private final String receiverUsername;
    public WinMessage(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public void accept(BroadcastMessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
