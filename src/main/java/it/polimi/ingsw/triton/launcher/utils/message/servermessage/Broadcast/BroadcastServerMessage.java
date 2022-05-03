package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.Message;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.BroadcastMessageVisitor;

public abstract class BroadcastServerMessage extends Message {

    public void accept(BroadcastMessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
    public abstract void accept(ServerMessageVisitor messageVisitor);
}
