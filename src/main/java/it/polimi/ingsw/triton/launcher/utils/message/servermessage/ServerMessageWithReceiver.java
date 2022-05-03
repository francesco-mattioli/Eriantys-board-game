package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public class ServerMessageWithReceiver extends ServerMessage {
    private String receiverUsername;

    public ServerMessageWithReceiver(String receiverUsername){
        this.receiverUsername = receiverUsername;
    }
    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        //to make abstract
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }
}
