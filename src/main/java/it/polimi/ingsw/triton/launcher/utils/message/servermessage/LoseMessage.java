package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;

/**
 * This message communicates the losing to a player.
 */
public class LoseMessage extends BroadcastServerMessage {
    private final String winnerPlayer;
    public LoseMessage(String winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }

    public String getWinnerPlayer() {
        return winnerPlayer;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
