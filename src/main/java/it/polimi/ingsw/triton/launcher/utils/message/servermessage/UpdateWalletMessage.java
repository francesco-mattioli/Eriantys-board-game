package it.polimi.ingsw.triton.launcher.utils.message.servermessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the current player that his wallet is increased adding 1 coin.
 */
public class UpdateWalletMessage extends ServerMessageWithReceiver{
    public UpdateWalletMessage(String receiverUsername) {
        super(receiverUsername);
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }

}
