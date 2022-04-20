package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class PlayersNumberRequest extends Message{
    public PlayersNumberRequest() {
        super(Game.NAME_SERVER, MessageType.PLAYERSNUMBER_REQUEST);
    }
}
