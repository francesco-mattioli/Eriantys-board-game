package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the current player that his wallet is increased adding 1 coin.
 */
public class UpdateWalletMessage extends ServerMessage{
    public UpdateWalletMessage(String receiverUsername) {
        super(MessageType.UPDATE_WALLET, receiverUsername);
    }
}
