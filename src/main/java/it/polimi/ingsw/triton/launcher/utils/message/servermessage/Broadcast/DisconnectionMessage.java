package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

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
