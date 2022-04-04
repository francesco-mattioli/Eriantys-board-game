package it.polimi.ingsw.triton.launcher.model;

import java.util.ArrayList;

public class MotherNature {

    private Island position;

    public MotherNature(Island island) {
        this.position=island;
    }

    public Island getPosition() {
        return position;
    }

    public Island move(AssistantCard assistantCard, int steps, ArrayList<Island> islands) {
        // TODO implement here
        return null;
    }

    public int getIndexOfOppositeIsland(){
        int currentIndex=position.getId();
        return (currentIndex+6)%12;
    }
}
