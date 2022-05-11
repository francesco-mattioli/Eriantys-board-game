package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

import java.io.Serializable;
import java.util.ArrayList;

public class MotherNature implements Serializable {

    private Island islandOn;
    private int additionalSteps;

    public MotherNature(Island islandOn) {
        this.islandOn = islandOn;
        this.additionalSteps = 0;
    }

    /**
     *  @return This method returns the island where MotherNature is
     */
    public Island getPosition() {
        return islandOn;
    }

    /**
     * Moves MotherNature onto another island
     * @param assistantCard specifies the played card, witch contains information about the max number of steps
     * @param steps specifies the number of steps that MotherNature has to do
     * @param islands specifies the ArrayList of Islands
     * @return returns the islands where MotherNature has been moved on
     * @throws IllegalArgumentException if the request steps are more than the number permitted
     */
    public Island move(AssistantCard assistantCard, int steps, ArrayList<Island> islands) throws IllegalClientInputException {
        int maxSteps = assistantCard.getType().getMaxSteps() + additionalSteps;
        if (steps > maxSteps)
            throw new IllegalClientInputException(ErrorTypeID.TOO_MANY_MOTHERNATURE_STEPS);
        islandOn = nextMotherNaturePosition(islandOn, steps, islands);
        return islandOn;
    }


    /**
     * @param islandOn specifies the island where MotherNature is now
     * @param steps specifies the number of steps that MotherNature has to do
     * @param islands specifies the ArrayList of Islands
     * @return returns the Island where MotherNature is going to be moved
     */
    private Island nextMotherNaturePosition(Island islandOn, int steps, ArrayList<Island> islands) {
        int positionIndex = islands.indexOf(islandOn);
        return islands.get((positionIndex + steps) % islands.size());
    }

    /**
     * @param islands specifies the ArrayList of Islands
     * @return returns the MotherNature's position opposite island
     */
    public int getIndexOfOppositeIsland(ArrayList<Island> islands) {
        return (islandOn.getId() + (islands.size() / 2)) % (islands.size());
    }

    /**
     * This method resets additionalSteps to 0
     */
    public void resetAdditionalSteps() {
        additionalSteps = 0;
    }

    public int getAdditionalSteps() {
        return additionalSteps;
    }


    /**
     * This method sets the number of additional steps that MotherNature can do
     * @param additionalSteps the number of additional steps that MotherNature can do
     */
    public void setAdditionalSteps(int additionalSteps) {
        this.additionalSteps = additionalSteps;
    }


    public void setIslandOn(Island islandOn) {
        this.islandOn = islandOn;
    }
}
