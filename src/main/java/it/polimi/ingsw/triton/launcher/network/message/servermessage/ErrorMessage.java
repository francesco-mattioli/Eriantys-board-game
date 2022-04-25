package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.network.message.ErrorTypeID;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

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
