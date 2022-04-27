package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class ErrorMessage extends ServerMessage {
    private ErrorTypeID errorTypeID;
    private String receiverUsername;
    public ErrorMessage(ErrorTypeID errorTypeID, String receiverUsername) {
        super(MessageType.ERROR, receiverUsername);
        this.errorTypeID = errorTypeID;
    }

    @Override
    public String toString(){
        return errorTypeID.getDescription();
    }
}
