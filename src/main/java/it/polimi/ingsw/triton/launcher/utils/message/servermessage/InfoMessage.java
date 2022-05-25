package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

/**
 * This abstract class is extended by all the messages that are sent
 * by the server to communicate some updates to all the clients.
 */
public abstract class InfoMessage extends ServerMessage {

    public void accept(InfoMessageVisitor visitor) {
        visitor.visit(this);
    }
}
