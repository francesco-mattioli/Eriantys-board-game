package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class PlayersNumbersAndModeRequest extends Message{
    public PlayersNumbersAndModeRequest() {
        super(Game.NAME_SERVER, MessageType.PLAYERSNUMBER_REQUEST);
    }
}
