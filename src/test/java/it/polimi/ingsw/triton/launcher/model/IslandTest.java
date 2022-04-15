package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect06;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect08;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect09;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.player.SchoolBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    private ArrayList<Player> players;
    Player p1, p2;
    private Island island1, island2;
    private Player[] professors;

    @BeforeEach
    void setup(){
        players = new ArrayList<Player>();
        p1 = new Player("Pippo");
        p1.setSchoolBoard(TowerColor.BLACK, 2);
        p2 = new Player("Pluto");
        p2.setSchoolBoard(TowerColor.WHITE, 2);
        players.add(p1);
        players.add(p2);
        island1 = new Island(1);
        island2 = new Island(2);
        professors = new Player[5];
        professors[0] = p1;
        professors[1] = p1;
        professors[2] = null;
        professors[3] = p2;
        professors[4] = p1;
        island1.addStudent(Color.YELLOW);
        island1.addStudent(Color.BLUE);
        island1.addStudent(Color.GREEN);
        island1.addStudent(Color.RED);
        island1.addStudent(Color.PINK);
    }

    @Test
    void defaultInfluence(){
        assertEquals(3, island1.calculateInfluence(p1, professors, island1.getDominator()));
    }

    @Test
    void strategy09Influence(){
        island1.setInfluenceStrategy(new InfluenceStrategyWithEffect09(Color.BLUE));
        assertEquals(2, island1.calculateInfluence(p1, professors, island1.getDominator()));
    }

    @Test
    void strategy08Influence(){
        island1.setInfluenceStrategy(new InfluenceStrategyWithEffect08());
        assertEquals(5, island1.calculateInfluence(p1, professors, island1.getDominator()));
    }

    @Test
    void updateInfluence(){
        island1.updateInfluence(players, professors);
        assertEquals(p1, island1.getDominator());
    }

    /**
     * This test verifies what happens when tho players have the same influence on an island and there are no towers
     */
    @Test
    void updateInfluenceEquals(){
        island1.addStudent(Color.RED);
        island1.addStudent(Color.RED);
        island1.updateInfluence(players, professors);
        assertNull(island1.getDominator());
    }

    /**
     * This test verifies if the influence of tower is correctly calculated
     */
    @Test
    void defaultInfluenceWithTowers(){
        island1.updateInfluence(players, professors);
        assertEquals(4, island1.calculateInfluence(p1, professors, island1.getDominator()));
    }

    /**
     * This test verifies il the dominator il correctly calculated when some towers are already on the island
     */
    @Test
    void updateInfluenceWithTowers(){
        island1.addStudent(Color.RED);
        island1.updateInfluence(players, professors);
        island1.addStudent(Color.RED);
        island1.updateInfluence(players, professors);
        assertEquals(p1, island1.getDominator());
    }

    /**
     * This test verifies the limit situation when two players have the same influence, but there are already towers on the island
     */
    @Test
    void updateInfluenceEqualsWithTowers(){
        island1.addStudent(Color.RED);
        island1.updateInfluence(players, professors);
        island1.addStudent(Color.RED);
        island1.addStudent(Color.RED);
        island1.updateInfluence(players, professors);
        assertEquals(p1, island1.getDominator());
    }

    /**
     * This test verifies the limit situation when a player overtakes the actual dominator
     */
    @Test
    void updateInfluenceWithChangeDomination(){
        island1.addStudent(Color.RED);
        island1.updateInfluence(players, professors);
        island1.addStudent(Color.RED);
        island1.addStudent(Color.RED);
        island1.addStudent(Color.RED);
        island1.updateInfluence(players, professors);
        assertEquals(p2, island1.getDominator());
    }

    /**
     * This test verifies the limit situation when two players have the same influence, but there are already towers on the island
     */
    @Test
    void stategy06InfluenceEqualsWithTowers(){
        island1.setInfluenceStrategy(new InfluenceStrategyWithEffect06());
        island1.updateInfluence(players, professors);
        assertEquals(3, island1.calculateInfluence(p1, professors, island1.getDominator()));
    }
}