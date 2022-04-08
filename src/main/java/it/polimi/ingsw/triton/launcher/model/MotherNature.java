package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;

public class MotherNature {

    private Island position;
    private int additionalSteps;

    public MotherNature(Island island) {
        this.position=island;
        this.additionalSteps = 0;
    }

    public Island getPosition() {
        return position;
    }

    //need to be tested
    public Island move(AssistantCard assistantCard, int steps, ArrayList<Island> islands) throws IllegalArgumentException {
        int maxSteps = assistantCard.getAssistantCardType().getMaxSteps() + additionalSteps;
        if (steps > maxSteps)
            throw new IllegalArgumentException();
        int positionIndex = islands.indexOf(position);
        position = islands.get((positionIndex+steps)%islands.size());
        return position;
    }

    public int getIndexOfOppositeIsland(){
        int currentIndex=position.getId();
        return (currentIndex+6)%12;
    }

    public void resetAdditionalSteps(){
        additionalSteps = 0;
    }

    public int getAdditionalSteps() {
        return additionalSteps;
    }

    public void setAdditionalSteps(int additionalSteps) {
        this.additionalSteps = additionalSteps;
    }
}
