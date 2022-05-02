package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates the win to the winner player.
 */
public class WinMessage extends ServerMessage {
    private String receiverUsername;
    public WinMessage(String usernameWinner) {
        this.receiverUsername=receiverUsername;
    }

    public void accept(BroadcastMessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
