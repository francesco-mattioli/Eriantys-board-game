package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoWithReceiverMessage;

/**
 * This message is sent by the server to communicate to the current player that his wallet is increased adding 1 coin.
 * */
public class UpdateWalletMessage extends InfoWithReceiverMessage {
    private final int wallet;
    public UpdateWalletMessage(String receiverUsername, int wallet) {
        super(receiverUsername);
        this.wallet = wallet;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public int getWallet() {
        return wallet;
    }
}
