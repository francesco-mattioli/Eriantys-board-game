package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

import java.util.List;

/**
 * This message is sent by the server to communicate which players have tied.
 */
public class TieMessage extends InfoMessage {
    private final List<String> players;

    public TieMessage(List<String> players) {
        this.players = players;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public List<String> getPlayers() {
        return players;
    }
}
