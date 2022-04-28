package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class GameModeRequest extends ServerMessage {
    private String receiverUsername;
    public GameModeRequest(String receiverUsername) {
        super(MessageType.GAMEMODE_REQUEST, receiverUsername);
    }
}
