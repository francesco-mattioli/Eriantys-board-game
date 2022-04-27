package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class PlayersNumberRequest extends ServerMessage {
    private String receiverUsername;
    public PlayersNumberRequest(String receiverUsername) {
        super(MessageType.PLAYERSNUMBER_REQUEST, receiverUsername);
    }
}
