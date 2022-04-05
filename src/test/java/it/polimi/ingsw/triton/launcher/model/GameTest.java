package it.polimi.ingsw.triton.launcher.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void createIslands() {
        Game game = new Game();

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
        Game game = new Game(); //prova
        game.createIslands();
        game.setupMotherNature();
        assertNotNull(game.getMotherNature());
        assertNotNull(game.getMotherNature().getPosition());
        assertTrue(game.getMotherNature().getPosition().getId() >= 0);
        assertTrue(game.getMotherNature().getPosition().getId() < 12);
    }

    @Test
    void setupBag() {
        Game game = new Game();
        game.setupBag();
        assertNotNull(game.getBag());
        assertFalse(game.getBag().isEmpty());
        for(Integer i: game.getBag().getStudents()){
            assertEquals(2, i);
        }
    }

    @Test
    void setupIslands() {
        Game game = new Game();
        game.setupIslands();
        assertTrue(game.getBag().isEmpty());
        for(Island island: game.getIslands()){
            if(island.getId() == game.getMotherNature().getPosition().getId() || island.getId() == game.getMotherNature().getIndexOfOppositeIsland())
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


    // TO DO
    @Test
    public void setupCloudTiles(){
        Game game = new Game();
        Bag bag = new Bag();
        bag.fillBag();
        ArrayList<CloudTile> cloudTiles = new ArrayList<>();
        cloudTiles.add(new CloudTile(0));
        cloudTiles.add(new CloudTile(1));


        for(CloudTile cloudTile: cloudTiles){
            for(int i=0; i<3;i++){
                cloudTile.addStudents(bag.drawStudent());
            }
        }

        int numOfStudentsOnCloudTile=0;
        for(CloudTile cloudTile: cloudTiles){
            int[] students=cloudTile.getStudents();
            for(int i=0;i<Color.values().length;i++){
                numOfStudentsOnCloudTile+=students[i];
            }
            assertEquals(3,numOfStudentsOnCloudTile);
        }
    }
}