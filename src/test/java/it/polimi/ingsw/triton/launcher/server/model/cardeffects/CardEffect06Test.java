package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyWithEffect06;
import it.polimi.ingsw.triton.launcher.server.model.islands.IslandManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect06Test {
    private IslandManager islandManager;

    @BeforeEach
    void setUp() {
        islandManager = new IslandManager();
    }

    @AfterEach
    void tearDown() {
        islandManager = null;
    }

    @Test
    void execute() {
        new CardEffect06(islandManager.getIslands()).execute();
        try {
            assertInstanceOf(InfluenceStrategyWithEffect06.class, islandManager.getIslandByID(5).getInfluenceStrategy());
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }
}