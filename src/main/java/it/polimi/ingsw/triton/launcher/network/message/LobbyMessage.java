package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;

import java.util.ArrayList;

public class LobbyMessage extends Message{
    private ArrayList<String> onlineNicknames;
    private int maxNumberPlayers;
    public LobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers) {
        super(Game.NAME_SERVER, MessageType.LOBBY);
        this.onlineNicknames = onlineNicknames;
        this.maxNumberPlayers = maxNumberPlayers;
    }
}
