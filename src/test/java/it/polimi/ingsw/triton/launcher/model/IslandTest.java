package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
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
        p1.setSchoolBoard(TowerColor.BLACK);
        p2 = new Player("Pluto");
        p2.setSchoolBoard(TowerColor.WHITE);
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
    void influence(){
        assertEquals(3, island1.calculateInfluence(p1, professors, island1.getDominator()));
    }

    @Test
    void UpdateInfluence(){
        island1.updateInfluence(players, professors);
        assertEquals(p1, island1.getDominator());
    }
}