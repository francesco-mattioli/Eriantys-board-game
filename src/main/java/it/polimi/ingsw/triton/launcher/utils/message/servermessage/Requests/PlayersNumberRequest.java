package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public class PlayersNumberRequest extends ServerMessage {
    private String receiverUsername;
    public PlayersNumberRequest(String receiverUsername) {
        super(MessageType.PLAYERSNUMBER_REQUEST, receiverUsername);
    }
}
