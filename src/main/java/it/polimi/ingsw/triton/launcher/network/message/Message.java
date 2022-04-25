package it.polimi.ingsw.triton.launcher.network.message;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

}
