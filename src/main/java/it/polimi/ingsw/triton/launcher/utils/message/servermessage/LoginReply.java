package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class LoginReply extends ServerMessage {
    public LoginReply() {
        super(MessageType.LOGIN_REPLY);
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
