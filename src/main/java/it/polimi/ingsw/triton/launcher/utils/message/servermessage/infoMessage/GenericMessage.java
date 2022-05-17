package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message is sent by the server to communicate generic information.
 * It's often used to print a string (the parameter stringMessage) on client view.
 */
public class GenericMessage extends InfoMessage {
    private final String stringMessage;
    public GenericMessage(String stringMessage) {
        this.stringMessage = stringMessage;
    }

    public String getStringMessage() {
        return stringMessage;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
