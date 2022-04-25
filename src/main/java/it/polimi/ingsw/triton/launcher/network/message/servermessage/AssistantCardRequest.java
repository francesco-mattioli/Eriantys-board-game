package it.polimi.ingsw.triton.launcher.network.message.servermessage;

import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class AssistantCardRequest extends ServerMessage {
    private String receiverUsername;
    public AssistantCardRequest(String receiverUsername) {
        super(MessageType.ASSISTANT_CARD_REQUEST, receiverUsername);
    }
}
