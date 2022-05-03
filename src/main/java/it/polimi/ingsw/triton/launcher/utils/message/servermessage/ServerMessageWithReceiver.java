package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public abstract class ServerMessageWithReceiver extends ServerMessage {
    private final String receiverUsername;

    public ServerMessageWithReceiver(String receiverUsername){
        this.receiverUsername = receiverUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }
}
