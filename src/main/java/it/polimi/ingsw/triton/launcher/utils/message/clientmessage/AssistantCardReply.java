package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class AssistantCardReply extends ClientMessage {
    private final AssistantCard chosenAssistantCard;

    public AssistantCardReply(String username, AssistantCard chosenAssistantCard) {
        super(MessageType.ASSISTANT_CARD_REPLY, username);
        this.chosenAssistantCard = chosenAssistantCard;
    }

    public AssistantCard getChosenAssistantCard() {
        return chosenAssistantCard;
    }
}
