package it.polimi.ingsw.triton.launcher.server.model.islands;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {

    private List<Island> islands;
    private MotherNature motherNature;

    @BeforeEach
    void setupIslandsAndMotherNature(){
        islands = new ArrayList<>();
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
        assertThrows(IllegalClientInputException.class, () -> motherNature.move(new AssistantCard(AssistantCardType.EAGLE), 5, islands));
    }

    /**
     * Tests if the method calculates correctly the opposite island to mother nature.
     */
    @Test
    void oppositeIsland(){
        assertEquals(4, motherNature.getIndexOfOppositeIsland(islands));
    }

    /**
     * Tests if the number of additional steps of mother nature is set again to 0.
     */
    @Test
    void testResetAdditionalSteps(){
        motherNature.setAdditionalSteps(2);
        assertEquals(2, motherNature.getAdditionalSteps());
        motherNature.resetAdditionalSteps();
        assertEquals(0, motherNature.getAdditionalSteps());
    }

    /**
     * Tests if the mother nature position is set correctly.
     */
    @Test
    void testSetIslandOn(){
        motherNature.setIslandOn(islands.get(2));
        assertEquals(islands.get(2), motherNature.getPosition());
    }
}