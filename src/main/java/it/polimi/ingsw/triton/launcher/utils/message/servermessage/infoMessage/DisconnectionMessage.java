package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to the players that one player has disconnected and so,
 * they will be disconnected instantly.
 */
public class DisconnectionMessage extends InfoMessage {

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
