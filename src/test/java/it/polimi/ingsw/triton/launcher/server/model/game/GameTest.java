package it.polimi.ingsw.triton.launcher.server.model.game;

import it.polimi.ingsw.triton.launcher.server.model.AssistantCard;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect08;
import it.polimi.ingsw.triton.launcher.server.model.enums.*;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyDefault;
import it.polimi.ingsw.triton.launcher.server.model.player.AssistantDeck;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorStrategyDefault;
import it.polimi.ingsw.triton.launcher.utils.exceptions.ChangeTurnException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        game = Game.instance(2);
        player1 = new Player("TestPlayer1");
        game.getPlayers().add(player1);
        player2 = new Player("TestPlayer2");
        game.getPlayers().add(player2);
        game.setCurrentPlayer(player1);
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
        assertEquals(player1, game.getCurrentPlayer());
    }

    /**
     * Tests if the number of students on each island (except the mother nature position and her opposite one) is 1.
     */
    @Test
    void testNumberOfStudentsOnIslands(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        assertEquals(1, Arrays.stream(game.getIslandManager().nextIsland(game.getIslandManager().getMotherNature().getPosition()).getStudents()).sum());
    }

    /**
     * Tests if the number of students on mother nature position and her opposite is 0.
     */
    @Test
    void testNumberOfStudentsOnMotherNatureIsland(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        assertEquals(0, Arrays.stream(game.getIslandManager().getMotherNature().getPosition().getStudents()).sum());
        try {
            assertEquals(0, Arrays.stream(game.getIslandManager().getIslandByID(game.getIslandManager().getMotherNature().getIndexOfOppositeIsland(game.getIslandManager().getIslands())).getStudents()).sum());
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests if the number of students in each entrance, after the setup method, is 7 (two-players game).
     */
    @Test
    void testNumberOfStudentsInEntrance(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        assertEquals(7, Arrays.stream(player1.getSchoolBoard().getEntrance()).sum());
    }

    /**
     * Tests if the number of cloud tiles created corresponds to the number of players in the game.
     */
    @Test
    void testNumberOfCloudTiles(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        assertEquals(game.getMaxNumberOfPlayers(), game.getCloudTiles().size());
    }

    /**
     * Tests if the number of students in the bag after the setup method is 100.
     */
    @Test
    void testNumberOfStudentsInBagAfterSetup(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        assertEquals(100, Arrays.stream(game.getBag().getStudents()).sum());
    }

    /**
     * Tests if the cloud tiles are correctly reset in the planning phase.
     */
    @Test
    void testResetCloudTiles(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        try {
            assertFalse(game.getCloudTileById(1).isAlreadyUsed());
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests if the cloud tiles are filled with three students (two-player mode).
     */
    @Test
    void testNumberOfStudentsOnCloudTile(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        try {
            assertEquals(3, Arrays.stream(game.getCloudTileById(1).getStudents()).sum());
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests if the cloud tiles remain empty when the bag is already empty.
     */
    @Test
    void testFillCloudTilesWithEmptyBag(){
        Arrays.fill(game.getBag().getStudents(), 0);
        game.createCloudTiles();
        game.addStudentsToCloudTiles();
        int sum = 0;
        for(CloudTile cloudTile: game.getCloudTiles()){
            sum += Arrays.stream(cloudTile.getStudents()).sum();
        }
        assertNotEquals(6, sum);
    }

    /**
     * Tests if the cloud tiles aren't filled completely when the bag becomes empty during the execution
     * of the method.
     */
    @Test
    void testFillCloudTilesWithPartiallyEmptyBag(){
        Arrays.fill(game.getBag().getStudents(), 1);
        game.createCloudTiles();
        game.addStudentsToCloudTiles();
        int sum = 0;
        for(CloudTile cloudTile: game.getCloudTiles()){
            sum += Arrays.stream(cloudTile.getStudents()).sum();
        }
        assertEquals(5, sum);
    }

    /**
     * Tests if the cloud tiles are filled when there are exactly six students in the bag and so, the attribute
     * notFullCloudTiles is set to true.
     */
    @Test
    void testFillCloudTilesWithExactlySixStudents(){
        game.getBag().addStudent(Color.BLUE);
        game.getBag().addStudent(Color.BLUE);
        game.getBag().addStudent(Color.BLUE);
        game.getBag().addStudent(Color.BLUE);
        game.getBag().addStudent(Color.BLUE);
        game.getBag().addStudent(Color.BLUE);
        game.createCloudTiles();
        game.addStudentsToCloudTiles();
        assertTrue(game.isNotFullCloudTiles());
    }

    /**
     * Tests if current player's move counter is incremented when he moves a student from his entrance.
     */
    @Test
    void testUpdatePlayerMoveCounter(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player1.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        int player1MoveCounter = player1.getMoveCounter();
        game.setCurrentPlayer(player1);
        try {
            game.executeActionMoveStudentToDiningRoom(Color.BLUE);
            game.executeActionMoveStudentToIsland(Color.BLUE, 5);
        } catch (LastMoveException | IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(player1MoveCounter + 2, player1.getMoveCounter());
    }

    /**
     * Tests if the method throws an exception when current player has just done his last move.
     */
    @Test
    void testFinishMoves(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player1.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        player1.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        game.setCurrentPlayer(player1);
        try {
            game.executeActionMoveStudentToDiningRoom(Color.BLUE);
            game.executeActionMoveStudentToDiningRoom(Color.BLUE);
        } catch (LastMoveException | IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertThrows(LastMoveException.class, () -> game.executeActionMoveStudentToDiningRoom(Color.BLUE));
        assertEquals(0, player1.getMoveCounter());
    }

    /**
     * Tests if mother nature is moved correctly when the number of steps the current player inserted
     * is allowed.
     */
    @Test
    void testNewPositionOfMotherNatureWhenStepsAreCorrect(){
        game.setCurrentPlayer(player1);
        player1.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.EAGLE));
        int oldMotherNatureId = game.getIslandManager().getMotherNature().getPosition().getId();
        int newMotherNatureId = oldMotherNatureId + 2;
        try {
            game.moveMotherNature(2);
        } catch (IllegalClientInputException | EndGameException | ChangeTurnException e) {
            throw new RuntimeException(e);
        }
        if(oldMotherNatureId == 10)
            newMotherNatureId = 0;
        if(oldMotherNatureId == 11)
            newMotherNatureId = 1;
        assertEquals(newMotherNatureId, game.getIslandManager().getMotherNature().getPosition().getId());
    }

    /**
     * Tests if mother nature is moved correctly when the number of steps the current player inserted exceeds
     * the maximum possible.
     */
    @Test
    void testNewPositionOfMotherNatureWhenStepsAreNotCorrect(){
        game.setCurrentPlayer(player1);
        player1.setLastPlayedAssistantCard(new AssistantCard(AssistantCardType.EAGLE));
        assertThrows(IllegalClientInputException.class, () -> game.moveMotherNature(8));
    }

    /**
     * Tests if current player's move counter is incremented when he moves a student from his entrance.
     */
    @Test
    void testMoveStudentToIslandThatNotExists(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.getSchoolBoard().addStudentIntoEntrance(Color.BLUE);
        game.setCurrentPlayer(player1);
        assertThrows(IllegalClientInputException.class, () -> game.executeActionMoveStudentToIsland(Color.BLUE, 13));
    }

    /**
     * Tests if the cloud tile selected by the player becomes already used.
     */
    @Test
    void testPlayerChoosesAvailableCloudTile(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        try {
            game.chooseCloudTile(game.getCloudTileById(0));
            assertTrue(game.getCloudTileById(0).isAlreadyUsed());
        } catch (IllegalClientInputException | ChangeTurnException | EndGameException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests if the method throws an exception when the player selected a cloud tile which is already used.
     */
    @Test
    void testPlayerChoosesACloudTileAlreadyUsed(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        try {
            game.chooseCloudTile(game.getCloudTileById(0));
        } catch (IllegalClientInputException | ChangeTurnException | EndGameException e) {
            throw new RuntimeException(e);
        }
        assertThrows(IllegalClientInputException.class, () -> game.chooseCloudTile(game.getCloudTiles().get(0)));
    }

    /**
     * Tests if all the strategies are set again to default at the end of turn.
     */
    @Test
    void testResetStrategiesAtTheEndOfTurn(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        try {
            game.chooseCloudTile(game.getCloudTileById(0));
        } catch (IllegalClientInputException | ChangeTurnException | EndGameException e) {
            throw new RuntimeException(e);
        }
        assertInstanceOf(ProfessorStrategyDefault.class, game.getProfessorsManager().getProfessorStrategy());
        assertEquals(0, game.getIslandManager().getMotherNature().getAdditionalSteps());
        try {
            assertInstanceOf(InfluenceStrategyDefault.class, game.getIslandManager().getIslandByID(4).getInfluenceStrategy());
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tests if the new current player is set correctly.
     */
    @Test
    void testSetNewCurrentPlayer(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        game.setCurrentPlayer(game.getPlayers().get(0));
        try {
            game.chooseCloudTile(game.getCloudTileById(0));
        } catch (IllegalClientInputException | ChangeTurnException | EndGameException e) {
            assertTrue(true);
        }
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    /**
     * Tests if the game ends because the bag is empty after choose cloud tile phase.
     */
    @Test
    void testEndGameBecauseBagIsEmpty(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        game.setCurrentPlayer(game.getPlayers().get(1));
        Arrays.fill(game.getBag().getStudents(), 0);
        try {
            game.getCloudTileById(0).setStudents(Color.BLUE);
            game.getCloudTileById(0).setStudents(Color.BLUE);
            game.getCloudTileById(0).setStudents(Color.BLUE);
        } catch (IllegalClientInputException e){
            throw new RuntimeException();
        }
        assertThrows(EndGameException.class, () -> game.chooseCloudTile(game.getCloudTileById(0)));
    }

    /**
     * Tests if the game goes to planning phase after the last player chose his cloud tile.
     */
    @Test
    void testChangeTurnAfterChooseCloudTile(){
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setWizard(Wizard.GREEN);
        game.setup();
        game.setCurrentPlayer(game.getPlayers().get(1));
        assertThrows(ChangeTurnException.class, () -> game.chooseCloudTile(game.getCloudTileById(0)));
    }

    /**
     * Tests if the method throws an exception because use character card is forbidden in normal mode.
     */
    @Test
    void testUseCharacterCardNotAllowed(){
        assertThrows(IllegalClientInputException.class, () -> game.useCharacterCard(player1, 2));
    }

    /**
     * Tests if the method throws an exception because use character card is forbidden in normal mode.
     */
    @Test
    void testDrawCharacterCardNotAllowed(){
        assertThrows(IllegalClientInputException.class, () -> game.drawCharacterCards());
    }

    /**
     * Tests if the method throws an exception because apply a card effect is forbidden in normal mode.
     */
    @Test
    void testApplyCharacterCardEffectNotAllowed(){
        assertThrows(IllegalClientInputException.class, () -> game.applyCharacterCardEffect(8, new CardEffect08(game.getIslandManager().getIslands(), game.getCurrentPlayer())));
    }

    /**
     * Tests if the method throws an exception because use character card is forbidden in normal mode.
     */
    @Test
    void testGetCharacterCardByIdNotAllowed(){
        assertThrows(IllegalClientInputException.class, () -> game.getCharacterCardByID(3));
    }

    /**
     * Tests if the method throws an exception because use character card is forbidden in normal mode.
     */
    @Test
    void testGetCharacterCardsNotAllowed(){
        assertThrows(IllegalClientInputException.class, () -> game.getCharacterCards());
    }

    /**
     * Tests if the player is removed correctly.
     */
    @Test
    void testRemovePlayerWithUsername(){
        int oldNumPlayers = game.getPlayers().size();
        game.removePlayer("TestPlayer2");
        assertEquals(oldNumPlayers - 1, game.getPlayers().size());
        assertEquals("TestPlayer1", game.getPlayers().get(0).getUsername());
    }

    /**
     * Tests if the number of islands created is twelve.
     */
    @Test
    void checkNumberOfIslands() {
        assertEquals(12, game.getIslandManager().getIslands().size());
    }

    /**
     * Tests if mother nature is onto an island at the beginning of the game.
     */
    @Test
    void checkMotherNaturePosition() {
        assertNotNull(game.getIslandManager().getMotherNature().getPosition());
    }

    /**
     * This test checks if game creates three cloud tiles when there are three players.
     */
    @Test
    void checkCreateCloudTilesWithThreePlayers() {
        game.endGame(true);
        game = Game.instance(3);
        game.addPlayer("TestPlayer1");
        game.getPlayerByUsername("TestPlayer1").setSchoolBoard(TowerColor.BLACK, 3);
        game.getPlayerByUsername("TestPlayer1").setAssistantDeck(new AssistantDeck(Wizard.BLUE));
        game.addPlayer("TestPlayer2");
        game.getPlayerByUsername("TestPlayer2").setSchoolBoard(TowerColor.WHITE, 3);
        game.getPlayerByUsername("TestPlayer2").setAssistantDeck(new AssistantDeck(Wizard.GREEN));
        game.addPlayer("TestPlayer3");
        game.getPlayerByUsername("TestPlayer3").setSchoolBoard(TowerColor.GREY, 3);
        game.getPlayerByUsername("TestPlayer3").setAssistantDeck(new AssistantDeck(Wizard.YELLOW));
        game.setup();
        assertEquals(3, game.getCloudTiles().size());
    }

    /**
     * This method checks if game add nine students into the entrance of every player when the game has three players.
     */
    @Test
    void checkIfEachPlayerHasNineStudentsIntoEntrance() {
        game.endGame(true);
        game = Game.instance(3);
        game.addPlayer("TestPlayer1");
        game.getPlayerByUsername("TestPlayer1").setSchoolBoard(TowerColor.BLACK, 3);
        game.getPlayerByUsername("TestPlayer1").setAssistantDeck(new AssistantDeck(Wizard.BLUE));
        game.addPlayer("TestPlayer2");
        game.getPlayerByUsername("TestPlayer2").setSchoolBoard(TowerColor.WHITE, 3);
        game.getPlayerByUsername("TestPlayer2").setAssistantDeck(new AssistantDeck(Wizard.GREEN));
        game.addPlayer("TestPlayer3");
        game.getPlayerByUsername("TestPlayer3").setSchoolBoard(TowerColor.GREY, 3);
        game.getPlayerByUsername("TestPlayer3").setAssistantDeck(new AssistantDeck(Wizard.YELLOW));
        game.setup();
        assertEquals(9, Arrays.stream(game.getPlayers().get(0).getSchoolBoard().getEntrance()).sum());
    }

   @Test
    void checksIfEachCloudTilesHasFourStudents(){
       game.endGame(true);
       game = Game.instance(3);
       game.addPlayer("TestPlayer1");
       game.getPlayerByUsername("TestPlayer1").setSchoolBoard(TowerColor.BLACK, 3);
       game.getPlayerByUsername("TestPlayer1").setAssistantDeck(new AssistantDeck(Wizard.BLUE));
       game.addPlayer("TestPlayer2");
       game.getPlayerByUsername("TestPlayer2").setSchoolBoard(TowerColor.WHITE, 3);
       game.getPlayerByUsername("TestPlayer2").setAssistantDeck(new AssistantDeck(Wizard.GREEN));
       game.addPlayer("TestPlayer3");
       game.getPlayerByUsername("TestPlayer3").setSchoolBoard(TowerColor.GREY, 3);
       game.getPlayerByUsername("TestPlayer3").setAssistantDeck(new AssistantDeck(Wizard.YELLOW));
       game.setup();
       assertEquals(4, Arrays.stream(game.getCloudTiles().get(0).getStudents()).sum());
    }

    /**
     * Tests if the method returns the correct cloud tile when exists.
     */
    @Test
    void testGetCloudTileByIdWhenExists() {
        player1.setSchoolBoard(TowerColor.BLACK, 2);
        player1.setWizard(Wizard.BLUE);
        player2.setSchoolBoard(TowerColor.WHITE, 2);
        player2.setWizard(Wizard.GREEN);
        game.setup();
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
     * Tests if the method launches an exception when the cloud tile doesn't exist.
     */
    @Test
    void testGetCloudTileByIdWhenNotExists() {
        assertThrows(IllegalClientInputException.class, () -> game.getCloudTileById(20));
    }
}

