package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;

/**
 * This message communicates to the player that the general coin supply is empty, so
 * he won't receive an additional coin after moving a student onto school board.
 */
public class EmptyGeneralCoinSupplyMessage extends ServerMessage{
    private final String receiverUsername;

    public EmptyGeneralCoinSupplyMessage(String receiverUsername){
        this.receiverUsername = receiverUsername;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }

    public String getReceiverUsername() {
        return receiverUsername;
    }
}
