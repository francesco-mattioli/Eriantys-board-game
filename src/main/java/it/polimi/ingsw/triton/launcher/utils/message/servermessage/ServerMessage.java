package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

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
