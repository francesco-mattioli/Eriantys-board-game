package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.ServeOneClient;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;

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
