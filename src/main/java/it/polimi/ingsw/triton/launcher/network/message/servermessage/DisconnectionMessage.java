package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class DisconnectionMessage extends BroadcastServerMessage {
    private final String disconnectedUsername;
    public DisconnectionMessage(String disconnectedUsername) {
        super(MessageType.DISCONNECTION);
        this.disconnectedUsername = disconnectedUsername;
    }

    public String getDisconnectedUsername() {
        return disconnectedUsername;
    }
}
