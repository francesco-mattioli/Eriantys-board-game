package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;

public class MotherNature {

    private Island islandOn;
    private int additionalSteps;

    public MotherNature(Island islandOn) {
        this.islandOn = islandOn;
        this.additionalSteps = 0;
    }

    public Island getPosition() {
        return islandOn;
    }

    //need to be tested
    public Island move(AssistantCard assistantCard, int steps, ArrayList<Island> islands) throws IllegalArgumentException {
        int maxSteps = assistantCard.getAssistantCardType().getMaxSteps() + additionalSteps;
        if (steps > maxSteps)
            throw new IllegalArgumentException("The number of steps exceed the maximum possible!");
        islandOn = nextMotherNaturePosition(islandOn, steps, islands);
        return islandOn;
    }

    private Island nextMotherNaturePosition(Island islandOn, int steps, ArrayList<Island> islands) {
        int positionIndex = islands.indexOf(islandOn);
        return islands.get((positionIndex + steps) % islands.size());
    }


    public int getIndexOfOppositeIsland(ArrayList<Island> islands) {
        return (islandOn.getId() + (islands.size() / 2)) % (islands.size());
    }

    public void resetAdditionalSteps() {
        additionalSteps = 0;
    }

    public int getAdditionalSteps() {
        return additionalSteps;
    }

    public void setAdditionalSteps(int additionalSteps) {
        this.additionalSteps = additionalSteps;
    }


}
