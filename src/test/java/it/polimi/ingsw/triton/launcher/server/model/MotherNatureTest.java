package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {

    private ArrayList<Island> islands;
    private MotherNature motherNature;

    @BeforeEach
    void setupIslandsAndMotherNature(){
        islands = new ArrayList<Island>();
        for(int i=0; i<12; i++){
            islands.add(new Island(i));
        }
        motherNature = new MotherNature(islands.get(10));
    }

    /**
     * Tests if mother nature moved correctly when she did 5 steps
     * without additional steps.
     */
    @Test
    void moveMotherNatureWithoutAdditionalSteps(){
        Island position = null;
        try {
            position = motherNature.move(new AssistantCard(AssistantCardType.TURTLE), 5, islands);
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        assertEquals(position, islands.get(3));
    }

    /**
     * Tests if mother nature moved correctly when she did 5 steps
     * with two additional steps.
     */
    @Test
    void moveMotherNatureWithAdditionalSteps(){
        motherNature.setAdditionalSteps(2);
        Island island = null;
        try {
            island = motherNature.move(new AssistantCard(AssistantCardType.DOG), 5, islands);
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        assertEquals(island, islands.get(3));
    }

    /**
     * Tests if mother nature launches an exception when the player chooses to move her
     * with a number of steps which is not correct.
     */
    @Test
    void moveMotherNatureWithIllegalArgument(){
        assertThrows(IllegalClientInputException.class, () -> {motherNature.move(new AssistantCard(AssistantCardType.EAGLE), 5, islands);});
    }

    /**
     * Tests if the method calculates correctly the opposite island to mother nature.
     */
    @Test
    void oppositeIsland(){
        assertEquals(4, motherNature.getIndexOfOppositeIsland(islands));
    }
}