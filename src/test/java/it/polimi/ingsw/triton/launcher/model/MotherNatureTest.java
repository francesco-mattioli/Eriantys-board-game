package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureTest {
    @Test
    void moveMotherNatureWithoutAdditionalSteps(){
        ArrayList<Island> islands = new ArrayList<Island>();
        for(int i=0; i<12; i++){
            islands.add(new Island(i));
        }
        MotherNature motherNature = new MotherNature(islands.get(10));
        Island island = motherNature.move(new AssistantCard(AssistantCardType.TURTLE), 10, islands);
        assertEquals(island, islands.get(8));
    }

    @Test
    void moveMotherNatureWithAdditionalSteps(){
        ArrayList<Island> islands = new ArrayList<Island>();
        for(int i=0; i<12; i++){
            islands.add(new Island(i));
        }
        MotherNature motherNature = new MotherNature(islands.get(10));
        motherNature.setAdditionalSteps(2);
        Island island = motherNature.move(new AssistantCard(AssistantCardType.DOG), 9, islands);
        assertEquals(island, islands.get(7));
    }

    @Test
    void moveMotherNatureWithIllegalArgument(){
        ArrayList<Island> islands = new ArrayList<Island>();
        for(int i=0; i<12; i++){
            islands.add(new Island(i));
        }
        MotherNature motherNature = new MotherNature(islands.get(10));
        assertThrows(IllegalArgumentException.class, () -> {motherNature.move(new AssistantCard(AssistantCardType.EAGLE), 6, islands);});
    }
}