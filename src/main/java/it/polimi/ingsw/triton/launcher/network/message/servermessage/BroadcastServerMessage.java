package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class BroadcastServerMessage extends Message {

    public BroadcastServerMessage(MessageType messageType) {
        super(messageType);
    }
}
