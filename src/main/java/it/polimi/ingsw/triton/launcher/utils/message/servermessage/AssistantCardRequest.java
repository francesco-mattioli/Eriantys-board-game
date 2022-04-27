package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message asks to the player to play an assistant card which is in his deck.
 */
public class AssistantCardRequest extends ServerMessage {
    private String receiverUsername;
    private AssistantDeck assistantDeck;
    public AssistantCardRequest(String receiverUsername, AssistantDeck assistantDeck) {
        super(MessageType.ASSISTANT_CARD_REQUEST, receiverUsername);
        this.assistantDeck = assistantDeck;
    }

    @Override
    public String getReceiverUsername() {
        return receiverUsername;
    }

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }
}
