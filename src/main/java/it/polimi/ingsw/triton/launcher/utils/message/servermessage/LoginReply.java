package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class LoginReply extends ServerMessage {
    private boolean isNicknameAccepted;
    private String receiverUsername;
    public LoginReply(Boolean isNicknameAccepted, String receiverUsername) {
        super(MessageType.LOGIN_REPLY, receiverUsername);
        this.isNicknameAccepted = isNicknameAccepted;
    }

    public boolean isNicknameAccepted() {
        return isNicknameAccepted;
    }
}
