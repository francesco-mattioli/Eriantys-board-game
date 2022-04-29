package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.view.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;

public abstract class ServerMessage extends BroadcastServerMessage {
    private String usernameReceiver;

    public ServerMessage(MessageType messageType,String usernameReceiver) {
        super(messageType);
        this.usernameReceiver=usernameReceiver;
    }

    public ServerMessage(MessageType messageType) {
        super(messageType);
    }

    public void accept(MessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
    public abstract void accept(ClientVisitor messageVisitor);
}
