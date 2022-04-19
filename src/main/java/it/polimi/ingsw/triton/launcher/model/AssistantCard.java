package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;


public class AssistantCard {

    /**
     * the type of assistant card
     */
    private final AssistantCardType assistantCardType;

    /**
     * Assigns the type of assistantCard to the Assistant card.
     *
     * @param assistantCardType the assistant card type
     */
    public AssistantCard(AssistantCardType assistantCardType) {
        this.assistantCardType = assistantCardType;
    }

    /**
     * Gets assistant card type.
     *
     * @return the assistant card type
     */
    public AssistantCardType getType() {
        return assistantCardType;
    }
}
