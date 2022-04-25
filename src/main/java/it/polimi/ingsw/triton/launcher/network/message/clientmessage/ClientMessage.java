package it.polimi.ingsw.triton.launcher.network.message.clientmessage;

import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class ClientMessage extends Message {

    public ClientMessage(MessageType messageType) {
        super(messageType);
    }
}
