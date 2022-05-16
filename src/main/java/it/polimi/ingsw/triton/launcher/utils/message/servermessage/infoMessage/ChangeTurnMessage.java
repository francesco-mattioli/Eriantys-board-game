package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to new current player that now it's his turn.
 * To the other players, it communicates the username of the new current player.
 */
public class ChangeTurnMessage extends InfoMessage {
    private final String currentUsername;
    public ChangeTurnMessage(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
