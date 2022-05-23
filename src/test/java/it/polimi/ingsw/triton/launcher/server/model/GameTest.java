package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.game.Game;
import it.polimi.ingsw.triton.launcher.server.model.game.GameMode;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.islands.IslandManager;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.ChangeTurnException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private GameMode game;
    private Player player1;
    private Player player2;
    private Player player3;
    private IslandManager islandManager;

    @BeforeEach
    public void setUp() {
        game = Game.instance(2);
        player1 = new Player("TestPlayer1");
        game.getPlayers().add(player1);
        player2 = new Player("TestPlayer2");
        game.getPlayers().add(player2);
        game.setCurrentPlayer(player1);
        //game.setup();
    }

    @AfterEach
    public void tearDown() {
        game.endGame(true);
    }

    /**
     * Tests if the method addPlayer throws an exception when the username is blank.
     */
    @Test
    void testAddPlayerWhenUsernameIsBlank(){
        assertThrows(IllegalArgumentException.class, () -> game.addPlayer(""));
    }

    /**
     * Tests if the method addPlayer throws an exception when the username is a space.
     */
    @Test
    void testAddPlayerWhenUsernameIsSpace(){
        assertThrows(IllegalArgumentException.class, () -> game.addPlayer(" "));
    }

    /**
     * Tests if the method addPlayer throws an exception when the username is the command of play character card.
     */
    @Test
    void testAddPlayerWhenUsernameIsCharacterCardCommand(){
        assertThrows(IllegalArgumentException.class, () -> game.addPlayer("--playCC"));
    }

    /**
     * Tests if the method addPlayer throws an exception when the username is already used by another player.
     */
    @Test
    void testAddPLayerWithUsernameAlreadyChosen(){
        assertThrows(IllegalArgumentException.class, () -> game.addPlayer("TestPlayer1"));
    }

    /**
     * Tests if the first player becomes the current player.
     */
    @Test
    void testCurrentPlayerAfterAddPlayerIfFirst(){
        game.getPlayers().clear();
        game.getPlayers().add(player1);
        assertEquals(player1, game.getCurrentPlayer());
    }

    /**
     * Tests if the method chooseTowerColor throws an exception when the player selects a tower color already chosen.
     */
    @Test
    void testChooseTowerColorAlreadySelected(){
        try {
            game.chooseTowerColor(player1, TowerColor.BLACK);
        } catch (IllegalClientInputException | ChangeTurnException e) {
            throw new RuntimeException(e);
        }
        assertThrows(IllegalClientInputException.class, () -> game.chooseTowerColor(player2, TowerColor.BLACK));
    }

    /**
     * Tests if the tower color is set correctly to the player when not already used.
     */
    @Test
    void testChooseCorrectTowerColor(){
        try {
            game.chooseTowerColor(player1, TowerColor.BLACK);
        } catch (IllegalClientInputException | ChangeTurnException e) {
            throw new RuntimeException(e);
        }
        assertEquals(TowerColor.BLACK, player1.getSchoolBoard().getTowerColor());
    }

    /**
     * Tests if the array of towerColor chosen is updated correctly when the player selects his tower color and so, the
     * current player is updated.
     */
    @Test
    void testUpdateTowerColorChosenAndCurrentPlayer(){
        try {
            game.chooseTowerColor(player1, TowerColor.BLACK);
        } catch (IllegalClientInputException | ChangeTurnException e) {
            throw new RuntimeException(e);
        }
        assertTrue(game.getTowerColorChosen()[TowerColor.BLACK.ordinal()]);
        assertEquals(player2, game.getCurrentPlayer());
    }

    /**
     * Tests if the method throws an exception when a player selects grey tower color but the game is for two
     * players so that color is forbidden.
     */
    @Test
    void testColorGreySelectedButOnlyTwoPlayers(){
        assertThrows(IllegalClientInputException.class, () -> game.chooseTowerColor(player1, TowerColor.GREY));
    }

    /**
     * Tests if the initial size of available wizards list is four.
     */
    @Test
    void testInitialSizeOfWizards(){
        assertEquals(4, game.getAvailableWizards().size());
    }

    /**
     * Tests if the method throws an exception when the wizard selected by the player is already used.
     */
    @Test
    void testWizardAlreadyChosen(){
        game.getAvailableWizards().remove(Wizard.BLUE);
        assertThrows(IllegalClientInputException.class, () -> game.chooseWizard(player2, Wizard.BLUE));
    }

    /**
     * Tests if the method sets correctly the wizard to the player when it's not used.
     */
    @Test
    void testSetWizardWhenAvailable(){
        try {
            game.chooseWizard(player1, Wizard.BLUE);
        } catch (IllegalClientInputException | ChangeTurnException e) {
            throw new RuntimeException(e);
        }
        assertEquals(Wizard.BLUE, player1.getAssistantDeck().getWizard());
        assertEquals(3, game.getAvailableWizards().size());
    }

    /**
     * Tests if the list of used assistant cards is updated when a player plays an assistant card.
     */
    @Test
    void testUpdateUsedAssistantCards(){
        player1.setAssistantDeck(new AssistantDeck(Wizard.BLUE));
        int oldUsedAssistantCardsSize = game.getUsedAssistantCards().size();
        try {
            game.chooseAssistantCard(player1, player1.getAssistantDeck().getAssistantDeck().get(2));
        } catch (IllegalClientInputException | ChangeTurnException e) {
            throw new RuntimeException(e);
        }
        assertEquals(oldUsedAssistantCardsSize + 1, game.getUsedAssistantCards().size());
    }

    /**
     * Tests if the list of used assistant cards is updated when a player plays an assistant card.
     */
    @Test
    void testUpdateGameStateAfterLastPlayerChoice(){
        player1.setAssistantDeck(new AssistantDeck(Wizard.BLUE));
        player2.setAssistantDeck(new AssistantDeck(Wizard.PURPLE));
        try {
            game.chooseAssistantCard(player1, player1.getAssistantDeck().getAssistantDeck().get(2));
            game.chooseAssistantCard(player2, player2.getAssistantDeck().getAssistantDeck().get(8));
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        } catch (ChangeTurnException e){
            assertTrue(true);
        }
        assertEquals(GameState.ACTION_PHASE, game.getGameState());
    }

    /**
     * Tests if the number of islands created is twelve.
     */
    @Test
    void checkNumberOfIslands() {
        assertEquals(12, game.getIslandManager().getIslands().size());
    }

    /**
     * Tests if mother nature is onto an island at the begin of the game.
     */
    @Test
    void checkMotherNaturePosition() {
        assertTrue(game.getIslandManager().getMotherNature().getPosition() != null);
    }

    /**
     * Tests if the number of students in the bag at the begin of the game is greater than zero.
     */
    @Test
    public void checkInitialNumberStudentsInBag() {
        for (Color color : Color.values())
            assertTrue(game.getBag().getStudents()[color.ordinal()] > 0);
    }

    /**
     * Tests if the number of students is 0 when the island contains mother nature.
     */
    @Test
    public void checkNumberOfStudentsOnIslandWithMotherNature() {
        Island islandWithMotherNature = game.getIslandManager().getMotherNature().getPosition();
        int numOfStudents = 0;
        for (Color color : Color.values())
            numOfStudents += islandWithMotherNature.getStudents()[color.ordinal()];
        assertEquals(0, numOfStudents);
    }

    /**
     * Tests if the number of students is 0 when the island is opposite to mother nature..
     */
    @Test
    public void checkNumberOfStudentsOnIslandOppositeToMotherNature() {
        int idIslandOppositeToMotherNature = (game.getIslandManager().getMotherNature().getPosition().getId() + 6) % 12;
        int numOfStudents = 0;
        for (Color color : Color.values())
            numOfStudents += game.getIslandManager().getIslands().get(idIslandOppositeToMotherNature).getStudents()[color.ordinal()];
        assertEquals(0, numOfStudents);
    }

    /**
     * Tests if the number of students is 1 onto other islands.
     */
    @Test
    public void checkNumberOfStudentsOnOtherIslands() {
        int idIslandWithMotherNature = game.getIslandManager().getMotherNature().getPosition().getId();
        int idIslandOppositeToMotherNature = (game.getIslandManager().getMotherNature().getPosition().getId() + 6) % 12;
        boolean numOfStudentsNotEqualsToOne = false;
        int numOfStudents = 0;
        for (Island island : game.getIslandManager().getIslands()) {
            if (island.getId() != idIslandWithMotherNature && island.getId() != idIslandOppositeToMotherNature) {
                for (Color color : Color.values()) {
                    numOfStudents += island.getStudents()[color.ordinal()];
                }
                if (numOfStudents != 1)
                    numOfStudentsNotEqualsToOne = true;
            }
            numOfStudents = 0;
        }
        assertFalse(numOfStudentsNotEqualsToOne);
    }

    /**
     * This test checks if game creates three cloudtiels when there are three players.
     */
    @Test
    public void checkCreateCloudTilesWithThreePlayers() {
        assertEquals(3, game.getCloudTiles().size());
    }

    /**
     * This method checks if game add nine students into the entrance of every players when the game has three players.
     */
    @Test
    public void checkIfEachPlayerHasNineStudentsIntoEntrance() {
        int numStudentsIntoEntrance;
        for (Player player : game.getPlayers()) {
            numStudentsIntoEntrance = 0;
            for (int i = 0; i < Color.values().length; i++) {
                numStudentsIntoEntrance += player.getSchoolBoard().getEntrance()[i];
            }
            assertEquals(9, numStudentsIntoEntrance);
        }
    }

    /**
     * Tests if the number of character cards created is three.
     */
    @Test
    public void checkCharacterCardsSizeIsThree() {
        try {
            assertEquals(3, game.getCharacterCards().size());
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
    }

   @Test
    public void checksIfEachCloudTilesHasFourStudents(){
        int numStudentsOnCloudTile;
        for (CloudTile cloudTile : game.getCloudTiles()) {
            numStudentsOnCloudTile = 0;
            for (int i = 0; i < Color.values().length; i++){
                numStudentsOnCloudTile += cloudTile.getStudents()[i];
            }
            assertEquals(4,numStudentsOnCloudTile);
        }
    }

   /* @Test
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
     * Tests if the id of the three character cards are different.
     */
    @Test
    public void checkCharacterCardsIdAreDifferent() throws IllegalClientInputException {
        boolean foundSameId = false;
        for (int i = 0; i < game.getCharacterCards().size() - 1; i++) {
            for (int j = i + 1; j < game.getCharacterCards().size(); j++) {
                if (game.getCharacterCards().get(i).getId() == game.getCharacterCards().get(j).getId())
                    foundSameId = true;
            }
        }
        assertFalse(foundSameId);
    }


    //test calculate winner
   /*@Test
    public void checkOneWinnerWithTower(){
        game.getPlayers().get(0).getSchoolBoard().moveTowerOntoIsland(2);
        game.getPlayers().get(1).getSchoolBoard().moveTowerOntoIsland(5);
        game.getPlayers().get(2).getSchoolBoard().moveTowerOntoIsland(3);
        assertEquals(game.getPlayers().get(1).getUsername(), game.calculateWinner());
    }


    @Test
    public void checkOneWinnerWithProfessors(){
        game.getPlayers().get(0).getSchoolBoard().moveTowerOntoIsland(2);
        game.getPlayers().get(1).getSchoolBoard().moveTowerOntoIsland(5);
        game.getPlayers().get(2).getSchoolBoard().moveTowerOntoIsland(5);
        game.getProfessors()[Color.BLUE.ordinal()] = game.getPlayers().get(1);
        game.getProfessors()[Color.GREEN.ordinal()] = game.getPlayers().get(2);
        game.getProfessors()[Color.YELLOW.ordinal()] = game.getPlayers().get(2);
        assertEquals(game.getPlayers().get(2).getUsername(), game.calculateWinner());

    }

    @Test
    public void checkTieWinners(){
        game.getPlayers().get(0).getSchoolBoard().moveTowerOntoIsland(2);
        game.getPlayers().get(1).getSchoolBoard().moveTowerOntoIsland(5);
        game.getPlayers().get(2).getSchoolBoard().moveTowerOntoIsland(5);
        game.getProfessors()[Color.BLUE.ordinal()] = game.getPlayers().get(1);
        game.getProfessors()[Color.GREEN.ordinal()] = game.getPlayers().get(2);
        assertEquals("Tie", game.calculateWinner());
    }*/

    /**
     * Tests if the method returns the correct island when exists.
     */
    @Test
    void testGetIslandByIdWhenExists() {
        Island island;
        int indexIsland = 4;
        try {
            island = game.getIslandManager().getIslandByID(indexIsland);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(indexIsland, island.getId());
    }

    /**
     * Tests if the method launches an exception when the island doesn't exists.
     */
    @Test
    void testGetIslandByIdWhenNotExists() {
        assertThrows(IllegalClientInputException.class, () -> game.getIslandManager().getIslandByID(20));
    }

    /**
     * Tests if the method returns the correct cloud tile when exists.
     */
    @Test
    void testGetCloudTileByIdWhenExists() {
        CloudTile cloudTile;
        int indexCloudTile = 1;
        try {
            cloudTile = game.getCloudTileById(indexCloudTile);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(indexCloudTile, cloudTile.getId());
    }

    /**
     * Tests if the method launches an exception when the cloud tile doesn't exists.
     */
    @Test
    void testGetCloudTileByIdWhenNotExists() {
        assertThrows(IllegalClientInputException.class, () -> game.getCloudTileById(20));
    }

    /**
     * Tests if the method returns the correct character card when exists.
     */
    @Test
    void testGetCharacterCardByIdWhenExists() {
        CharacterCard characterCard;
        int indexCharacterCard = 0;
        try {
            indexCharacterCard = game.getCharacterCards().get(1).getId();
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        try {
            characterCard = game.getCharacterCardByID(indexCharacterCard);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(indexCharacterCard, characterCard.getId());
    }

    /**
     * Tests if the method launches an exception when the character card doesn't exists.
     */
    @Test
    void testGetCharacterCardByIdWhenNotExists() {
        assertThrows(IllegalClientInputException.class, () -> game.getCharacterCardByID(20));
    }
}

