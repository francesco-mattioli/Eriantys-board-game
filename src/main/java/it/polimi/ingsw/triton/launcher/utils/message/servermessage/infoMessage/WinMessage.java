package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate the win to the winner player.
 * In the visitor client-side, this message is shown only to the winner.
 * Other players, they receive a loose message.
 */
public class WinMessage extends InfoMessage {
    private final String receiverUsername;
    public WinMessage(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public String getReceiverUsername(){
        return receiverUsername;
    }
}
