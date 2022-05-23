package it.polimi.ingsw.triton.launcher.server.model.islands;

import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyDefault;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyWithEffect08;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandManagerTest {
    private IslandManager islandManager;
    private List<Player> players;
    private Player p1;
    private Player p2;
    private Player[] professors;

    @BeforeEach
    void setUp() {
        islandManager = new IslandManager();
        players = new ArrayList<>();
        p1 = new Player("testP1");
        p2 = new Player("testP2");
        p1.setSchoolBoard(TowerColor.BLACK, 2);
        p2.setSchoolBoard(TowerColor.WHITE, 2);
        players.add(p1);
        players.add(p2);
        professors = new Player[5];
        professors[0] = p1;
        professors[1] = p2;
        professors[2] = p1;
        professors[3] = p2;
        professors[4] = p2;
    }

    @AfterEach
    void tearDown() {
        islandManager = null;
        p1 = null;
        p2 = null;
    }

    /**
     * Checks if the size of islands list is decreased when an island merged correctly with the next one.
     */
    @Test
    void mergeWithNextIslandWhenHaveSameDominator() {
        int numIslands = islandManager.getIslands().size();
        try {
            islandManager.getIslandByID(5).setDominator(p1);
            islandManager.getIslandByID(6).setDominator(p1);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        try {
            islandManager.mergeNearIslands(islandManager.getIslandByID(5), players, professors);
        } catch (EndGameException | IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(numIslands - 1, islandManager.getIslands().size());
    }

    /**
     * Checks if the size of islands list is decreased when an island merged correctly with the previous one.
     */
    @Test
    void mergeWithPreviousIslandWhenHaveSameDominator() {
        int numIslands = islandManager.getIslands().size();
        try {
            islandManager.getIslandByID(5).setDominator(p1);
            islandManager.getIslandByID(4).setDominator(p1);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        try {
            islandManager.mergeNearIslands(islandManager.getIslandByID(5), players, professors);
        } catch (EndGameException | IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(numIslands - 1, islandManager.getIslands().size());
    }

    /**
     * Checks if the size of islands list is the same because two islands didn't have the same dominator.
     */
    @Test
    void mergeNearIslandsWhenNotSameDominator() {
        int numIslands = islandManager.getIslands().size();
        try {
            islandManager.getIslandByID(5).setDominator(p1);
            islandManager.getIslandByID(6).setDominator(p2);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        try {
            islandManager.mergeNearIslands(islandManager.getIslandByID(5), players, professors);
        } catch (EndGameException | IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(numIslands, islandManager.getIslands().size());
    }

    /**
     * Checks if the size of islands list is not decreased when the island near mother nature position
     * has no dominator.
     */
    @Test
    void mergeNearIslandsWhenOneIslandHasNotDominator() {
        int numIslands = islandManager.getIslands().size();
        try {
            islandManager.getIslandByID(5).setDominator(p1);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        try {
            islandManager.mergeNearIslands(islandManager.getIslandByID(5), players, professors);
        } catch (EndGameException | IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(numIslands, islandManager.getIslands().size());
    }

    /**
     * Checks if it finds correctly an island when this exists.
     */
    @Test
    void testExistsIsland() {
        assertTrue(islandManager.existsIsland(5));
    }

    /**
     * Checks if it didn't find an island when this doesn't exist anymore.
     */
    @Test
    void testNotExistsIsland(){
        islandManager.getIslands().remove(5);
        assertFalse(islandManager.existsIsland(5));
    }

    /**
     * Checks if it returns the id of the island when this exists.
     */
    @Test
    void testGetIslandByIDWhenExists() {
        Island island = islandManager.getIslands().get(3);
        try {
            assertEquals(island, islandManager.getIslandByID(3));
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if it throws an exception when the island didn't exist.
     */
    @Test
    void testGetIslandByIDWhenNotExists() {
        islandManager.getIslands().remove(3);
        assertThrows(IllegalClientInputException.class, ()->islandManager.getIslandByID(3));
    }

    /**
     * Checks if the influence strategy of an island is set again to default.
     */
    @Test
    void testResetInfluenceStrategy() {
        try {
            islandManager.getIslandByID(5).setInfluenceStrategy(new InfluenceStrategyWithEffect08(p1));
            assertInstanceOf(InfluenceStrategyWithEffect08.class, islandManager.getIslandByID(5).getInfluenceStrategy());
            islandManager.resetIslandsInfluenceStrategy();
            assertInstanceOf(InfluenceStrategyDefault.class, islandManager.getIslandByID(5).getInfluenceStrategy());
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }
}