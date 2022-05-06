package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

public class DisconnectionMessage extends InfoMessage {
    //private final String disconnectedUsername;
    public DisconnectionMessage() {
    }

    /*public String getDisconnectedUsername() {
        return disconnectedUsername;
    }*/

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
