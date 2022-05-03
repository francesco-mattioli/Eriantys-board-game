package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class ErrorMessage extends ServerMessage {
    private ErrorTypeID errorTypeID;
    public ErrorMessage(ErrorTypeID errorTypeID) {
        this.errorTypeID = errorTypeID;
    }

    @Override
    public String toString(){
        return errorTypeID.getDescription();
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {

    }
}
