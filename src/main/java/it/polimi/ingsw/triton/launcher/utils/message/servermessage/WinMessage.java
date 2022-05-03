package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates the win to the winner player.
 */
public class WinMessage extends ServerMessageWithReceiver {
    public WinMessage(String receiverUsername) {
        super(receiverUsername);
    }

    public void accept(BroadcastMessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
