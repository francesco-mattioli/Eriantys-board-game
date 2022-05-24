package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.CloudTileReply;

/**
 * This message is sent by the server to ask the current player which cloud tile
 * wants to fetch students from it.
 */
public class CloudTileRequest extends AskMessage {

    public CloudTileRequest() {
        super.expectedResponseMessageClass= CloudTileReply.class;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
