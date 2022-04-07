package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    @Test
    void move(){
        ArrayList<Island> islands = new ArrayList<Island>();
        for(int i = 0; i < 7; i++)
            islands.add(new Island(i));
        Island position = islands.get(4);
        MotherNature motherNature = new MotherNature(position);
        AssistantCard a1 = new AssistantCard(AssistantCardType.CAT);
        AssistantCard a2 = new AssistantCard(AssistantCardType.DOG);
        motherNature.move(a1, 2, islands);
        assertTrue(motherNature.getPosition() == islands.get(6));
        motherNature.move(a2, 3, islands);
        assertTrue(motherNature.getPosition() == islands.get(2));
    }

    @Test
    void merge(){  ///need to be tested in combination with mergeIslands() in game
        ArrayList<Island> islands = new ArrayList<Island>();
        for(int i = 0; i<10; i++){
            islands.add(new Island(i));
        }
        islands.get(0).merge(islands.get(1));
        assertEquals(2, islands.get(0).getDim());
        islands.remove(1);
        islands.get(0).merge(islands.get(1));
        assertEquals(3, islands.get(0).getDim());
    }

    void influence(){

    }
}