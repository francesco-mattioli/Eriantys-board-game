package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the current player that his wallet is increased adding 1 coin.
 */
public class UpdateWalletMessage extends ServerMessage{
    private final String receiverUsername;
    public UpdateWalletMessage(String receiverUsername) {
        super(MessageType.UPDATE_WALLET);
        this.receiverUsername = receiverUsername;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }

    public String getReceiverUsername() {
        return receiverUsername;
    }
}
