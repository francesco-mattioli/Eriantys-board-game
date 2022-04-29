package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public class GameModeRequest extends ServerMessage {
    public GameModeRequest() {
        super(MessageType.GAMEMODE_REQUEST);
    }
}
