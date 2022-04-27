package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class LoginRequest extends ClientMessage {

    public LoginRequest(String username, MessageType messageType) {
        super(MessageType.LOGIN_REQUEST, username);
    }

}
