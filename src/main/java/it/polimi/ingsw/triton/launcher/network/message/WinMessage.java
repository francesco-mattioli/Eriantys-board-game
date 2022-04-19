package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

public class WinMessage extends Message{
    private String nicknameWinner;
    public WinMessage(String nicknameWinner) {
        super(Game.NAME_SERVER, MessageType.WIN);
        this.nicknameWinner = nicknameWinner;
    }
}
