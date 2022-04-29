package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This message asks to the player to play an assistant card which is in his deck.
 */
public class AssistantCardRequest extends ServerMessage {
    public AssistantCardRequest() {
        super(MessageType.ASSISTANT_CARD_REQUEST);
    }

}
