package it.polimi.ingsw.triton.launcher.network.message;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final String senderName;
    private final MessageType messageType;

    public Message(String username, MessageType messageType) {
        this.senderName = username;
        this.messageType = messageType;
    }

    public String getSenderName() {
        return senderName;
    }

    public MessageType getMessageType() {
        return messageType;
    }

}
