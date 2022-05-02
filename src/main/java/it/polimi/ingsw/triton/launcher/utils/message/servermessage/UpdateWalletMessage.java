package it.polimi.ingsw.triton.launcher.utils.message.servermessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the current player that his wallet is increased adding 1 coin.
 */
public class UpdateWalletMessage extends ServerMessage{
    private final String receiverUsername;
    public UpdateWalletMessage(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }

    public String getReceiverUsername() {
        return receiverUsername;
    }
}
