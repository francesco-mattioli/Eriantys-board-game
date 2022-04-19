package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
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

    @Test
    void moveMotherNatureWithoutAdditionalSteps(){
        Island position = motherNature.move(new AssistantCard(AssistantCardType.TURTLE), 5, islands);
        assertEquals(position, islands.get(3));
    }

    @Test
    void moveMotherNatureWithAdditionalSteps(){
        motherNature.setAdditionalSteps(2);
        Island island = motherNature.move(new AssistantCard(AssistantCardType.DOG), 5, islands);
        assertEquals(island, islands.get(3));
    }

    @Test
    void moveMotherNatureWithIllegalArgument(){
        assertThrows(IllegalArgumentException.class, () -> {motherNature.move(new AssistantCard(AssistantCardType.EAGLE), 5, islands);});
    }

    @Test
    void oppositeIsland(){
        assertEquals(4, motherNature.getIndexOfOppositeIsland(islands));
    }
}