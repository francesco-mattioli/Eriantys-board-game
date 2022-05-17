package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.Message;

/**
 * This abstract class is extended by all the messages that are sent by the server.
 */
public abstract class ServerMessage implements Message {

    public abstract void accept(ServerMessageVisitor messageVisitor);
}
