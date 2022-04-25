package it.polimi.ingsw.triton.launcher.network.message.clientmessage;

import it.polimi.ingsw.triton.launcher.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.network.message.Message;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class AssistantCardReply extends ClientMessage {
    private final AssistantCard chosenAssistantCard;
    private final String username;
    public AssistantCardReply(String username, AssistantCard chosenAssistantCard) {
        super(MessageType.ASSISTANT_CARD_REPLY);
        this.chosenAssistantCard = chosenAssistantCard;
        this.username = username;
    }


    public AssistantCard getChosenAssistantCard() {
        return chosenAssistantCard;
    }

    public String getUsername() {
        return username;
    }
}
