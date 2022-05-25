package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoWithReceiverMessage;

/**
 * This message is sent by the server to communicate to the player that the general coin supply is empty, so
 * he won't receive an additional coin after moving a student onto a specific position of his school board.
 */
public class EmptyGeneralCoinSupplyMessage extends InfoWithReceiverMessage {

    public EmptyGeneralCoinSupplyMessage(String receiverUsername) {
        super(receiverUsername);
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

}
