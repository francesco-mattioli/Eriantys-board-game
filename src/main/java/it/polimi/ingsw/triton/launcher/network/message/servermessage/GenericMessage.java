package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class GenericMessage extends ServerMessage {
    private String message;
    private String receiverUsername;
    public GenericMessage(String message, String receiverUsername) {
        super(MessageType.GENERIC, receiverUsername);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
