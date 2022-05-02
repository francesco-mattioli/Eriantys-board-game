package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast.BroadcastServerMessage;

/**
 * Communicates to the player that is his turn and to the others who is playing.
 */
public class YourTurnMessage extends BroadcastServerMessage {
    private final String currentUsername;
    public YourTurnMessage(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
