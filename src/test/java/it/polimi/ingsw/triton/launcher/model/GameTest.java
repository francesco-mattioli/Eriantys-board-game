package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.AssistantCardType;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    /**
     * Tests if the method launches an exception if the nickname is already chosen.
     */
    @Test
    public void addPlayerWhenUsernameIsChosen(){
        Player playerTest = new Player("TestPlayer1");
        assertThrows(IllegalArgumentException.class, ()->game.addPlayer(playerTest.getUsername()));
    }

    /**
     * Tests if the method launches an exception if the nickname is eriantys.
     */
    @Test
    public void addPlayerWhenUsernameIsEriantys(){
        Player playerTest = new Player("eriantys");
        assertThrows(IllegalArgumentException.class, ()->game.addPlayer(playerTest.getUsername()));
    }

    /**
     * Tests if the method launches an exception if the nickname is written in capital letters
     * but is already chosen.
     */
    @Test
    public void addPlayerWhenUsernameIsChosenInCapitalLetters(){
        Player playerTest = new Player("TESTPLAYER2");
        assertThrows(IllegalArgumentException.class, ()->game.addPlayer(playerTest.getUsername()));
    }

    /**
     * Tests if the array of chosen tower colors is updated correctly when a player
     * selects a tower color.
     */
    @Test
    public void checkTowerColorSelected(){
        assertTrue(game.getTowerColorChosen()[TowerColor.WHITE.ordinal()]);
    }

    /*@Test
    public void checkSortingPlayers(){
        player1.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.SNAKE));
        player2.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.TIGER));
        player3.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.CAT));
        game.sortPlayerPerTurn();
        assertEquals("TestPlayer2", game.getPlayers().get(0).getUsername());
        assertEquals("TestPlayer3", game.getPlayers().get(1).getUsername());
        assertEquals("TestPlayer1", game.getPlayers().get(2).getUsername());

    }*/

    /**
     * Tests if the method depositCoin launches an exception when the number of coins to deposit
     * is less than 0.
     */
    @Test
    public void depositCoinsWhenNumberOfCoinsIsLessThanZero(){
        assertThrows(IllegalArgumentException.class, ()->game.depositCoin(-2));
    }

    /**
     * Tests if the general coin supply is increased.
     */
    @Test
    public void depositCoinsWhenNumberOfCoinsIsCorrect(){
        int oldSupply = game.getGeneralCoinSupply();
        game.depositCoin(2);
        assertEquals(oldSupply + 2, game.getGeneralCoinSupply());
    }

    /**
     * Tests if the general coin supply is decreased by 1.
     */
    @Test
    public void withdrawCoinTest(){
        int oldSupply = game.getGeneralCoinSupply();
        game.withdrawCoin();
        assertEquals(oldSupply - 1, game.getGeneralCoinSupply());
    }

    /**
     * Tests if the method launches an exception when there are no coins to withdraw.
     */
    @Test
    public void withdrawCoinWhenSupplyHasNotCoins(){
        for(int i = 0; i < 20; i++){
            game.withdrawCoin();
        }
        assertThrows(RuntimeException.class, ()->game.withdrawCoin());
    }

    /**
     * Tests if the number of character cards created is three.
     */
    @Test
    public void checkCharacterCardsSizeIsThree(){
        assertEquals(3, game.getCharacterCards().size());
    }

    /**
     * Tests if the id of the three character cards are different.
     */
    @Test
    public void checkCharacterCardsIdAreDifferent(){
        boolean foundSameId = false;
        for(int i = 0; i < game.getCharacterCards().size() - 1; i++){
            for(int j = i + 1; j < game.getCharacterCards().size(); j++){
                if(game.getCharacterCards().get(i).getId() == game.getCharacterCards().get(j).getId())
                    foundSameId = true;
            }
        }
        assertFalse(foundSameId);
    }
}

