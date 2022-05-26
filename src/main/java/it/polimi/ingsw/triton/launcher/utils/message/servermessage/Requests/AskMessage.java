package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Requests;

import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.UseCharacterCardRequest;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class is extended by all the messages that are sent by the server
 * for requests.
 */
public abstract class AskMessage implements ServerMessage {
    protected final transient List<Class<?>> expectedResponseMessageClasses;

    protected AskMessage() {
        this.expectedResponseMessageClasses = new ArrayList<>();
        this.expectedResponseMessageClasses.add(UseCharacterCardRequest.class);
    }

    public List<Class<?>> getExpectedResponseMessageClasses() {
        return expectedResponseMessageClasses;
    }

}
