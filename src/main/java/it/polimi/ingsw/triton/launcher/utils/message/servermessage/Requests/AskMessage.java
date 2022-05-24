package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies.*;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

/**
 * This abstract class is extended by all the messages that are sent by the server
 * for requests.
 */
public abstract class AskMessage extends ServerMessage {
    protected Class<?> expectedResponseMessageClass;

    public abstract void accept(ServerMessageVisitor messageVisitor);

    public Class<?> getExpectedResponseMessageClass(){
        return expectedResponseMessageClass;
    }

}
