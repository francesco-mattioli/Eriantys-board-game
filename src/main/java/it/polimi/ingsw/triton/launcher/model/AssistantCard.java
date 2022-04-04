package it.polimi.ingsw.triton.launcher.model;

public class AssistantCard {
    private AssistantCardType assistantCardType;

    public AssistantCard(AssistantCardType assistantCardType){
        this.assistantCardType=assistantCardType;
    }

    public AssistantCardType getAssistantCardType() {
        return assistantCardType;
    }
}
