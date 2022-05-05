package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

import java.util.ArrayList;

public class LobbyMessage extends InfoMessage {
    private final ArrayList<String> onlineNicknames;
    private final int maxNumberPlayers;
    public LobbyMessage(ArrayList<String> onlineNicknames, int maxNumberPlayers) {
        this.onlineNicknames = onlineNicknames;
        this.maxNumberPlayers = maxNumberPlayers;
    }

    public ArrayList<String> getOnlineNicknames() {
        return onlineNicknames;
    }

    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
