package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.islands.IslandManager;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.playeractions.MoveStudentOntoIsland;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardEffect03Test {
    private IslandManager islandManager;
    private Player p1;
    private Player p2;
    private Player[] professors;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        islandManager = new IslandManager();
        p1 = new Player("playerTest1");
        p1.setSchoolBoard(TowerColor.BLACK, 2);
        p1.getSchoolBoard().addStudentIntoEntrance(Color.YELLOW);
        p2 = new Player("playerTest2");
        p2.setSchoolBoard(TowerColor.WHITE, 2);
        professors = new Player[5];
        professors[Color.GREEN.ordinal()] = p1;
        professors[Color.RED.ordinal()] = p2;
        professors[Color.YELLOW.ordinal()] = p1;
        professors[Color.PINK.ordinal()] = null;
        professors[Color.BLUE.ordinal()] = null;
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        try {
            new MoveStudentOntoIsland(p1.getSchoolBoard(), Color.YELLOW, islandManager.getMotherNature().getPosition()).execute();
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        players = null;
        islandManager = null;
        professors = null;
        p1 = null;
        p2 = null;
    }

    /**
     * Checks if it updates the influence correctly on the island selected by the player
     * where he wants to calculate the influence.
     */
    @Test
    void testUpdateInfluenceOnIslandWithCardEffect03() {
        try {
            new CardEffect03(islandManager.getMotherNature().getPosition(), islandManager, players, professors).execute();
            assertEquals(p1, islandManager.getMotherNature().getPosition().getDominator());
        } catch (EndGameException e) {
            throw new RuntimeException(e);
        }
    }
}