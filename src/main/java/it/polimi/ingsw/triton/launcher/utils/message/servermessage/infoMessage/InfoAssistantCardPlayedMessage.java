package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to the other players which assistant card
 * is played by the current player in the game.
 */
public class InfoAssistantCardPlayedMessage extends InfoMessage {
    private final AssistantCard assistantCardPlayed;
    private final String currentPlayerUsername;
    public InfoAssistantCardPlayedMessage(String currentPlayerUsername, AssistantCard assistantCardPlayed) {
        this.currentPlayerUsername = currentPlayerUsername;
        this.assistantCardPlayed = assistantCardPlayed;
    }

    public AssistantCard getAssistantCardPlayed() {
        return assistantCardPlayed;
    }

    public String getCurrentPlayerUsername() {
        return currentPlayerUsername;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
