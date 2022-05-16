package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

/**
 * This abstract class is extended by all the messages that are sent by the server
 * to communicate some updates to a specific client.
 */
public abstract class InfoWithReceiverMessage extends InfoMessage {
    protected final String receiverUsername;

    protected InfoWithReceiverMessage(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    @Override
    public void accept(InfoMessageVisitor visitor) {
        visitor.visit(this);
    }
}
