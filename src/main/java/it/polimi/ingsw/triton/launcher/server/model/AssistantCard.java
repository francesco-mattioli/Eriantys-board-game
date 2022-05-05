package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.enums.AssistantCardType;

import java.io.Serializable;


public class AssistantCard implements Serializable {

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

    @Override
    public String toString(){
        return "\t" + assistantCardType.name() + ", value: " + assistantCardType.getValue() + ", max steps: " + assistantCardType.getValue() + "\n";
    }
}
