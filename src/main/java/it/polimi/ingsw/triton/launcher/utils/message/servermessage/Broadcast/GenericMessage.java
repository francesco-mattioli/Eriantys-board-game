package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.client.ClientVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.ServerMessage;

public class GenericMessage extends ServerMessage {
    private String message;
    private String receiverUsername;
    public GenericMessage(String message, String receiverUsername) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void accept(ClientVisitor messageVisitor) {

    }
}
