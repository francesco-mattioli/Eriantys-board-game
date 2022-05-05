package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

public abstract class InfoWithReceiverMessage extends InfoMessage {
    protected final String receiverUsername;

    protected InfoWithReceiverMessage(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
}
