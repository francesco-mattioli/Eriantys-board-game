package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoWithReceiverMessage;

/**
 * This message is sent by the server to communicate to the new player that now he's logged in correctly.
 */
public class LoginReply extends InfoWithReceiverMessage {

    public LoginReply(String receiverUsername) {
        super(receiverUsername);
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }

}
