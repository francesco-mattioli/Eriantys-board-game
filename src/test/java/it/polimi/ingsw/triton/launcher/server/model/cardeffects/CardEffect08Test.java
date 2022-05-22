package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyWithEffect08;
import it.polimi.ingsw.triton.launcher.server.model.islands.IslandManager;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect08Test {
    private IslandManager islandManager;
    private Player currentPlayer;

    @BeforeEach
    void setUp() {
        islandManager = new IslandManager();
        currentPlayer = new Player("playerTest1");
    }

    @AfterEach
    void tearDown() {
        islandManager = null;
        currentPlayer = null;
    }

    /**
     * Checks if the method sets correctly the influence strategy for every island.
     */
    @Test
    void testSetCorrectlyInfluenceStrategy() {
        new CardEffect08(islandManager.getIslands(), currentPlayer).execute();
        try {
            assertInstanceOf(InfluenceStrategyWithEffect08.class, islandManager.getIslandByID(5).getInfluenceStrategy());
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }
}