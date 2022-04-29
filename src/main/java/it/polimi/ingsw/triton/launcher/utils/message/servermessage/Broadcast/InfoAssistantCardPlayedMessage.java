package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message is necessary to communicate to the other players which assistant card
 * has played the current player in the game.
 */
public class InfoAssistantCardPlayedMessage extends BroadcastServerMessage{
    private AssistantCard assistantCardPlayed;
    private String currentPlayerUsername;
    public InfoAssistantCardPlayedMessage(String currentPlayerUsername, AssistantCard assistantCardPlayed) {
        super(MessageType.INFO_ASSISTANT_CARD_PLAYED);
        this.currentPlayerUsername = currentPlayerUsername;
        this.assistantCardPlayed = assistantCardPlayed;
    }

    public AssistantCard getAssistantCardPlayed() {
        return assistantCardPlayed;
    }

    public String getCurrentPlayerUsername() {
        return currentPlayerUsername;
    }
}
