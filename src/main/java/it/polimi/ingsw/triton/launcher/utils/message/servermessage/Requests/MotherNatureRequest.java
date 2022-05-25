package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.MotherNatureReply;
/**
 * This message is sent by the server to ask the current player how many steps
 * mother nature has to do.
 */
public class MotherNatureRequest extends AskMessage {

    public MotherNatureRequest() {
        super.expectedResponseMessageClasses.add(MotherNatureReply.class);
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
