package it.polimi.ingsw.triton.launcher.network.message.clientmessage;

import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class LoginRequest extends ClientMessage {

    public LoginRequest(String username, MessageType messageType) {
        super(MessageType.LOGIN_REQUEST, username);
    }

}
