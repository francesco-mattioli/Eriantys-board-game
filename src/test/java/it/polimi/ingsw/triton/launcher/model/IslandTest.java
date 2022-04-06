package it.polimi.ingsw.triton.launcher.model;

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
        MotherNature motherNature = MotherNature.instance(position);
        AssistantCard a1 = new AssistantCard(AssistantCardType.CAT);
        AssistantCard a2 = new AssistantCard(AssistantCardType.DOG);
        motherNature.move(a1, 2, islands);
        assertTrue(motherNature.getPosition() == islands.get(6));
        motherNature.move(a2, 3, islands);
        assertTrue(motherNature.getPosition() == islands.get(2));
    }
}