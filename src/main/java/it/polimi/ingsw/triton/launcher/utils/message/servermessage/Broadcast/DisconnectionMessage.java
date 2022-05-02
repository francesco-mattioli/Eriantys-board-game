package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class DisconnectionMessage extends BroadcastServerMessage {
    private final String disconnectedUsername;
    public DisconnectionMessage(String disconnectedUsername) {
        this.disconnectedUsername = disconnectedUsername;
    }

    public String getDisconnectedUsername() {
        return disconnectedUsername;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
