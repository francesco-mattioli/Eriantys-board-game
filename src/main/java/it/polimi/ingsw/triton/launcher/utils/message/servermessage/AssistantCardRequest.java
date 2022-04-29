package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks to the player to play an assistant card which is in his deck.
 */
public class AssistantCardRequest extends ServerMessage {
    public AssistantCardRequest(String receiverUsername) {
        super(MessageType.ASSISTANT_CARD_REQUEST, receiverUsername);
    }

}
