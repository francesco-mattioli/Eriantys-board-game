package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

/**
 * This abstract class is extended by all the messages that are sent
 * by the server to communicate some updates to all clients.
 * This is not an interface because we want to distinguish InfoMessage and InfoMessageWithReceiver
 * and the accept method implemented in InfoMessage and InfoMessageWithReceiver allows doing so.
 * If an interface is used, the accept method for InfoMessageVisitor should be implemented in every
 * subclass of InfoMessage, causing code duplication.
 */
public abstract class InfoMessage implements ServerMessage {

    /**
     * This method allows the InfoMessageVisitor to distinguish a message with receiver and one without it.
     * @param visitor class that forwards the message to the client
     */
    public void accept(InfoMessageVisitor visitor) {
        visitor.visit(this);
    }
}
