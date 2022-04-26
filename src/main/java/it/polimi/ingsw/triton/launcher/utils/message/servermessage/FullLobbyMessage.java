package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class FullLobbyMessage extends ServerMessage {
    private String receiverUsername;
    public FullLobbyMessage(String receiverUsername) {
        super(MessageType.FULL_LOBBY, receiverUsername);
    }
}
