package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class BroadcastServerMessage extends Message {

    public BroadcastServerMessage(MessageType messageType) {
        super(messageType);
    }

    public void accept(MessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
}
