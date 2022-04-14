package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void setUp(){
        game = new Game(2);
        player1 = new Player("TestPlayer1");
        player2 = new Player("TestPlayer2");
        player3 = new Player("TestPlayer3");
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);
        game.getPlayers().add(player3);

    }

    @AfterEach
    public void tearDown(){
        game = null;
    }

    @Test
    void createIslands() {
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
        game.createIslands();
        game.setupMotherNature();
        assertNotNull(game.getMotherNature());
        assertNotNull(game.getMotherNature().getPosition());
        assertTrue(game.getMotherNature().getPosition().getId() >= 0);
        assertTrue(game.getMotherNature().getPosition().getId() < 12);
    }

    @Test
    void setupBag() {
        game.setupBag();
        assertNotNull(game.getBag());
        assertFalse(game.getBag().isEmpty());
        for(Integer i: game.getBag().getStudents()){
            assertEquals(2, i);
        }
    }

    @Test
    void setupIslands() {
        game.setupIslands();
        assertTrue(game.getBag().isEmpty());
        for(Island island: game.getIslands()){
            if(island.getId() == game.getMotherNature().getPosition().getId() || island.getId() == game.getMotherNature().getIndexOfOppositeIsland(game.getIslands()))
            {
                int sum = 0;
                for(int i=0; i<5; i++)
                {
                    sum += island.getStudents()[i];
                }
                assertEquals(0, sum);
            }
            else{
                int sum = 0;
                for(int i=0; i<5; i++)
                {
                    sum += island.getStudents()[i];
                }
                assertEquals(1, sum);
            }
        }
    }

    @Test
    void createCloudTiles(){
        assertEquals(3,game.getPlayers().size());
        assertEquals("TestPlayer1", game.getPlayers().get(0).getUsername());
        assertEquals("TestPlayer2", game.getPlayers().get(1).getUsername());
        game.createCloudTiles();
        assertEquals(3,game.getCloudTiles().size());
    }

    @Test
    void setupSchoolBoard()
    {
        Map<Player, TowerColor> m1 = new HashMap<Player, TowerColor>();
        m1.put(game.getPlayers().get(0), TowerColor.BLACK);
        Map<Player,TowerColor> m2 = new HashMap<Player, TowerColor>();
        m2.put(game.getPlayers().get(1), TowerColor.WHITE);
        game.setupSchoolboard(m1);
        game.setupSchoolboard(m2);
        assertEquals(TowerColor.BLACK, game.getPlayers().get(0).getSchoolBoard().getTowerColor());
        assertEquals(TowerColor.WHITE, game.getPlayers().get(1).getSchoolBoard().getTowerColor());
        /*game.addPlayer("Michele");
        Map<Player,TowerColor> m3 = new HashMap<Player, TowerColor>();
        m3.put(game.getPlayers().get(2), TowerColor.BLACK);
        game.setupSchoolboard(m3);
        assertEquals(TowerColor.BLACK, game.getPlayers().get(2).getSchoolBoard().getTowerColor());*/
    }

    @Test
    public void setupCloudTiles(){
        ArrayList<CloudTile> cloudTiles = new ArrayList<>();
        cloudTiles.add(new CloudTile(0));
        cloudTiles.add(new CloudTile(1));

        Random random = new Random();

        // adding some students without a logic
        for(CloudTile cloudTile: cloudTiles){
            for(int i=0; i<3;i++){
                cloudTile.addStudent(Color.values()[random.nextInt(Color.values().length)]);
            }
        }

        for(CloudTile cloudTile: cloudTiles){
            int sumOfStudents=0;
            int[] students=cloudTile.getStudents();
            for(int i=0;i<Color.values().length;i++){
                sumOfStudents+=students[i];
            }
            assertEquals(3,sumOfStudents);
        }
    }

    @Test
    public void checkSortingPlayers(){
        player1.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.SNAKE));
        player2.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.TIGER));
        player3.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.CAT));
        game.sortPlayerPerTurn();
        assertEquals("TestPlayer2", game.getPlayers().get(0).getUsername());
        assertEquals("TestPlayer1", game.getPlayers().get(2).getUsername());
        assertEquals("TestPlayer3", game.getPlayers().get(1).getUsername());

    }
}

