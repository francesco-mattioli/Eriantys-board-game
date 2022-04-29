package it.polimi.ingsw.triton.launcher.utils.message;

import it.polimi.ingsw.triton.launcher.client.view.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.MessageVisitor;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void accept(MessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }

    public abstract void accept(ClientVisitor messageVisitor);

}
