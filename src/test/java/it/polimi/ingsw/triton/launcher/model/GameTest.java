package it.polimi.ingsw.triton.launcher.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void createIslands() {
        Game game = new Game();
        game.createIslands();
        assertEquals(12,game.getIslands().size());
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