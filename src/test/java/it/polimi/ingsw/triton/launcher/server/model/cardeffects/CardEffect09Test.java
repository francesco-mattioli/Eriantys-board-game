package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyWithEffect09;
import it.polimi.ingsw.triton.launcher.server.model.islands.IslandManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CardEffect09Test {
    private IslandManager islandManager;

    @BeforeEach
    void setUp() {
        islandManager = new IslandManager();
    }

    @AfterEach
    void tearDown() {
        islandManager = null;
    }

    /**
     * Checks if the method sets correctly the influence strategy for every island.
     */
    @Test
    void testSetCorrectlyInfluenceStrategy() {
        new CardEffect09(islandManager.getIslands(), Color.YELLOW).execute();
        try {
            assertInstanceOf(InfluenceStrategyWithEffect09.class, islandManager.getIslandByID(5).getInfluenceStrategy());
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }
}