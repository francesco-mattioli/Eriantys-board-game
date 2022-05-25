package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

/**
 * This message is sent by the server to communicate an error that
 * occurs during an operation or after an input check.
 * The errorTypeID specifies which type of error is occurred.
 */
public class ErrorMessage extends ServerMessage {
    private final ErrorTypeID errorTypeID;

    public ErrorMessage(ErrorTypeID errorTypeID) {
        this.errorTypeID = errorTypeID;
    }

    public ErrorTypeID getErrorTypeID() {
        return errorTypeID;
    }

    @Override
    public String toString() {
        return errorTypeID.getDescription();
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
