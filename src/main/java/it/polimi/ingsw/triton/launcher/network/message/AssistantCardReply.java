package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.AssistantCard;

public class AssistantCardReply extends Message{
    private AssistantCard chosenAssistantCard;
    public AssistantCardReply(String nickname, AssistantCard chosenAssistantCard) {
        super(nickname, MessageType.ASSISTANT_CARD_REPLY);
        this.chosenAssistantCard = chosenAssistantCard;
    }
}
