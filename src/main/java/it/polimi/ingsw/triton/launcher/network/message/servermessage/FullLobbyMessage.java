package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class FullLobbyMessage extends ServerMessage {
    private String receiverUsername;
    public FullLobbyMessage(String receiverUsername) {
        super(MessageType.FULL_LOBBY, receiverUsername);
    }
}
