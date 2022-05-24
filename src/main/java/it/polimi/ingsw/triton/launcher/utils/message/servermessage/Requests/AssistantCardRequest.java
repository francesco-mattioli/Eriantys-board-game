package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.AssistantCardReply;


/**
 * This message is sent by the server to ask the player to play an assistant card which is in his deck.
 */
public class AssistantCardRequest extends AskMessage {

    public AssistantCardRequest() {
        super.expectedResponseMessageClass=AssistantCardReply.class;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
