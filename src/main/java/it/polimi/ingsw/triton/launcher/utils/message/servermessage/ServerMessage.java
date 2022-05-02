package it.polimi.ingsw.triton.launcher.utils.message.servermessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;

public abstract class ServerMessage extends BroadcastServerMessage {


    public void accept(BroadcastMessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
    public abstract void accept(ServerMessageVisitor messageVisitor);
}
