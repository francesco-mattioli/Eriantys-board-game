package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * Communicates to the player that is his turn.
 */
public class YourTurnMessage extends ServerMessage{
    public YourTurnMessage(MessageType messageType, String receiverUsername) {
        super(messageType, receiverUsername);
    }
}
