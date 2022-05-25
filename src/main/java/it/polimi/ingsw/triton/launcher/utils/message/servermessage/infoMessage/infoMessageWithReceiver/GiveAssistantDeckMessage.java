package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoWithReceiverMessage;

/**
 * This message is sent by the server to give to every player his assistant deck.
 */
public class GiveAssistantDeckMessage extends InfoWithReceiverMessage {
    private final AssistantDeck assistantDeck;

    public GiveAssistantDeckMessage(String receiverUsername, AssistantDeck assistantDeck) {
        super(receiverUsername);
        this.assistantDeck = assistantDeck;
    }

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
