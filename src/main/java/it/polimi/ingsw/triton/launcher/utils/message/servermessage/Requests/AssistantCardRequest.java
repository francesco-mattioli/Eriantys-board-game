package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;


/**
 * This message asks the player to play an assistant card which is in his deck.
 */
public class AssistantCardRequest extends AskMessage {

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
