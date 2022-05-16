package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate to the players that the bag is empty
 * and this round will be the last one.
 */
public class EmptyBagMessage extends InfoMessage {

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
