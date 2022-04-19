package it.polimi.ingsw.triton.launcher.network.message;

public class LoginRequest extends Message{
    public LoginRequest(String nickname, MessageType messageType) {
        super(nickname, MessageType.LOGIN_REQUEST);
    }
}
