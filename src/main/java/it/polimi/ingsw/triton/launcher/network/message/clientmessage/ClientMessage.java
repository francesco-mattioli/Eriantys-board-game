package it.polimi.ingsw.triton.launcher.network.message.clientmessage;

import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class ClientMessage extends Message {
    private final String username;

    public ClientMessage(MessageType messageType, String username) {
        super(messageType);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
