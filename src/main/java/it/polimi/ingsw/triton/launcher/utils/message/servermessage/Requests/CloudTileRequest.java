package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;

/**
 * This message is sent by the server to ask the current player which cloud tile
 * wants to fetch students from it.
 */
public class CloudTileRequest extends AskMessage {

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
