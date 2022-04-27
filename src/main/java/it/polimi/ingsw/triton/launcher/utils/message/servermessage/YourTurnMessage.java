package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * Communicates to the player that is his turn and to the others who is playing.
 */
public class YourTurnMessage extends BroadcastServerMessage{
    private String currentUsername;
    public YourTurnMessage(String currentUsername) {
        super(MessageType.YOUR_TURN);
        this.currentUsername = currentUsername;
    }
}
