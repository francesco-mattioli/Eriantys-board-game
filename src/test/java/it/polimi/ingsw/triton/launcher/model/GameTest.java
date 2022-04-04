package it.polimi.ingsw.triton.launcher.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void createIslands() {
        Game game = new Game();

        game.createIslands();
        assertEquals(12,game.getIslands().size());
        int j = 0; // int to check the island's id
        for (Island i: game.getIslands()){
            assertNotNull(i);
            assertEquals(1, i.getDim());
            assertEquals(j,i.getId());
            j++;
        }


    }

    @Test
    void setupMotherNature() {
    }

    @Test
    void setupBag() {
    }

    @Test
    void setupIslands() {
    }
}