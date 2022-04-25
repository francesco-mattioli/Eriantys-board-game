package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class ServerMessage extends BroadcastServerMessage{

    private final String receiverUsername;

    public ServerMessage(MessageType messageType, String receiverUsername) {
        super(messageType);
        this.receiverUsername = receiverUsername;
    }

    public String getReceiverUsername(){
        return receiverUsername;
    }
}
