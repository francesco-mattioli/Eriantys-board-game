package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect05Test {
    private Island island;
    private CharacterCard characterCard;

    @BeforeEach
    void setUp() {
        island = new Island(3);
        characterCard = new CharacterCard(5, 2, 0, new Bag());
    }

    @AfterEach
    void tearDown() {
        island = null;
        characterCard = null;
    }

    /**
     * Checks if the method throws an exception when the character card 05 has no entry tiles.
     */
    @Test
    void testAddEntryTileWhenCardHasNotEntryTiles() {
        for(int i = 0; i < 4; i++)
            characterCard.noEntryTiles--;
        assertThrows(IllegalClientInputException.class, () -> new CardEffect05(island, characterCard).execute());
    }

    /**
     * Checks if the number of no entry tiles on the island is increased after the execution
     * of effect 05.
     */
    @Test
    void testAddEntryTileOnIsland() {
        int oldNumEntryTiles = island.getNoEntryTiles();
        try {
            new CardEffect05(island, characterCard).execute();
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldNumEntryTiles + 1, island.getNoEntryTiles());
    }

    /**
     * Checks if the number of no entry tiles on the character card is decreased after the execution
     * of effect 05.
     */
    @Test
    void testRemoveEntryTileFromCard() {
        int oldNumEntryTiles = characterCard.noEntryTiles;
        try {
            new CardEffect05(island, characterCard).execute();
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldNumEntryTiles - 1, characterCard.noEntryTiles);
    }
}