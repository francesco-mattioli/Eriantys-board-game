package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.network.message.StudentsOnCloudTileMessage;
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
        game = new Game(3);
        game.addPlayer("TestPlayer1");
        game.addPlayer("TestPlayer2");
        game.addPlayer("TestPlayer3");
        game.chooseTowerColor(game.getPlayers().get(0),TowerColor.WHITE);
        game.chooseTowerColor(game.getPlayers().get(1),TowerColor.BLACK);
        game.chooseTowerColor(game.getPlayers().get(2),TowerColor.GREY);
        game.setup();
    }

    @AfterEach
    public void tearDown(){
        game = null;
    }

    @Test
    public void ThreeStudentsOnCloudTileAfterAddStudentsToCloudTiles(){
        int[] students= new int[5];
        students[0]=1;
        students[2]=1;
        students[4]=2;
        StudentsOnCloudTileMessage message= new StudentsOnCloudTileMessage(students,game.getCloudTiles().get(0));
        int[] oldStudentsInMessage= message.students().clone();
        game.addStudentsToCloudTiles(game.getCurrentPlayer(),message);
        for(int  i=0;i<Color.numOfColors();i++){
            assertEquals(game.getCloudTiles().get(0).getStudents()[i],oldStudentsInMessage[i]);
        }

    }

    /*
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
    void createCloudTiles() {
        assertEquals(3, game.getPlayers().size());
        assertEquals("TestPlayer1", game.getPlayers().get(0).getUsername());
        assertEquals("TestPlayer2", game.getPlayers().get(1).getUsername());
        game.createCloudTiles();
        assertEquals(3, game.getCloudTiles().size());
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
    */

    @Test
    public void checkSortingPlayers(){
        player1.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.SNAKE));
        player2.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.TIGER));
        player3.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.CAT));
        game.sortPlayerPerTurn();
        assertEquals("TestPlayer2", game.getPlayers().get(0).getUsername());
        assertEquals("TestPlayer3", game.getPlayers().get(1).getUsername());
        assertEquals("TestPlayer1", game.getPlayers().get(2).getUsername());

    }
}

