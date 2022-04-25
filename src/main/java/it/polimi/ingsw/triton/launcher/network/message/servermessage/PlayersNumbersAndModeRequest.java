package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class PlayersNumbersAndModeRequest extends ServerMessage {
    private String receiverUsername;
    public PlayersNumbersAndModeRequest(String receiverUsername) {
        super(MessageType.PLAYERSNUMBER_REQUEST, receiverUsername);
    }
}
