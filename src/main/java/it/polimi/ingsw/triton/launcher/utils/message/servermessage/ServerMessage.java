package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class ServerMessage extends BroadcastServerMessage{

    public ServerMessage(MessageType messageType) {
        super(messageType);
    }

    public void accept(MessageVisitor messageVisitor){
        messageVisitor.visit(this);
    }
}
