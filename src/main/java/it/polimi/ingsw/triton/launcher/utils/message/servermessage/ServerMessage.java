package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;

public abstract class ServerMessage extends BroadcastServerMessage {


    public void accept(BroadcastMessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
    public abstract void accept(ClientVisitor messageVisitor);
}
