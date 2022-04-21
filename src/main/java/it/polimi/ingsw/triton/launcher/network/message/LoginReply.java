package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class LoginReply extends Message{
    private boolean isNicknameAccepted;
    private String receiverNickname;
    public LoginReply(Boolean isNicknameAccepted, String receiverNickname) {
        super(Game.NAME_SERVER, MessageType.LOGIN_REPLY);
        this.isNicknameAccepted = isNicknameAccepted;
        this.receiverNickname = receiverNickname;
    }
}
