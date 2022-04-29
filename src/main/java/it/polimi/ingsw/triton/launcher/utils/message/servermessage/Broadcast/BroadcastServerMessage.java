package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.view.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.MessageVisitor;

public abstract class BroadcastServerMessage extends Message {

    public BroadcastServerMessage(MessageType messageType) {
        super(messageType);
    }

    public void accept(MessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
    public abstract void accept(ClientVisitor messageVisitor);
}
