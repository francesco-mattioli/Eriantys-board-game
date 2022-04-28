package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates the losing to a player.
 */
public class LoseMessage extends BroadcastServerMessage {
    private final String winnerPlayer;
    public LoseMessage(String winnerPlayer) {
        super(MessageType.LOSE);
        this.winnerPlayer = winnerPlayer;
    }

    public String getWinnerPlayer() {
        return winnerPlayer;
    }
}
