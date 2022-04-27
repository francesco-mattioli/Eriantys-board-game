package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class PlayersNumbersAndModeRequest extends ServerMessage {
    private String receiverUsername;
    public PlayersNumbersAndModeRequest(String receiverUsername) {
        super(MessageType.PLAYERSNUMBER_REQUEST, receiverUsername);
    }
}
