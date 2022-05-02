package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message gives to every player his assistant deck.
 */
public class GiveAssistantDeckMessage extends ServerMessage{
    private final AssistantDeck assistantDeck;
    private final String receiverUsername;
    public GiveAssistantDeckMessage(String receiverUsername, AssistantDeck assistantDeck) {
        this.receiverUsername=receiverUsername;
        this.assistantDeck = assistantDeck;
    }

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
