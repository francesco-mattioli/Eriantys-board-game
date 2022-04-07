package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;

public class AssistantCard {
    private AssistantCardType assistantCardType;

    public AssistantCard(AssistantCardType assistantCardType){
        this.assistantCardType=assistantCardType;
    }

    public AssistantCardType getAssistantCardType() {
        return assistantCardType;
    }
}
