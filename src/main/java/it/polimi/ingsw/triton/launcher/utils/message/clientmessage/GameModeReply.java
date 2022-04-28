package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class GameModeReply extends ClientMessage {
    private final boolean expertMode;

    public GameModeReply(String username, boolean expertMode) {
        super(MessageType.GAMEMODE_REPLY, username);
        this.expertMode = expertMode;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

}
