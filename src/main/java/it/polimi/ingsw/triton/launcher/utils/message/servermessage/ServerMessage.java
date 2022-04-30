package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;

public abstract class ServerMessage extends BroadcastServerMessage {

    public ServerMessage(MessageType messageType) {
        super(messageType);
    }

    public void accept(MessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
    public abstract void accept(ClientVisitor messageVisitor);
}
