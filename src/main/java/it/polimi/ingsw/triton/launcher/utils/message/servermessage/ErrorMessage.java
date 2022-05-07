package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

public class ErrorMessage extends ServerMessage {
    private final ErrorTypeID errorTypeID;
    public ErrorMessage(ErrorTypeID errorTypeID) {
        this.errorTypeID = errorTypeID;
    }

    public ErrorTypeID getErrorTypeID() {
        return errorTypeID;
    }

    @Override
    public String toString(){
        return errorTypeID.getDescription();
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
