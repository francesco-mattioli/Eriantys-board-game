package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class LoginReply extends Message{
    private boolean isNicknameAccepted;
    public LoginReply(Boolean isNicknameAccepted) {
        super(Game.NAME_SERVER, MessageType.LOGIN_REPLY);
        this.isNicknameAccepted = isNicknameAccepted;
    }
}
