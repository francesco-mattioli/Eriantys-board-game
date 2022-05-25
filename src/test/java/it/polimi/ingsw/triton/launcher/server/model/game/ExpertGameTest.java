package it.polimi.ingsw.triton.launcher.server.model.game;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect02;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.GameState;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpertGameTest {

    private GameMode expertGame;
    private Player p1;

    @BeforeEach
    public void setUp() {
        GameMode game = Game.instance(3);
        expertGame = new ExpertGame(game);
        p1 = new Player("TestPlayer1");
        expertGame.getPlayers().add(p1);
        Player p2 = new Player("TestPlayer2");
        expertGame.getPlayers().add(p2);
        Player p3 = new Player("TestPlayer3");
        expertGame.getPlayers().add(p3);
        expertGame.getBag().fillBag();

    }

    @AfterEach
    public void tearDown() {
        expertGame.getPlayers().clear();
        expertGame.endGame(true);
    }


    /**
     * Tests if the method drawCharacterCards draws three cards.
     */
    @Test
    void checkIfNumberOfCharCardIsThree() {
        try {
            expertGame.drawCharacterCards();
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(3, expertGame.getCharacterCards().size());
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests if every CharacterCard has the right cost.
     */
    @Test
    void checkCharacterCardCost(){
        try {
            expertGame.drawCharacterCards();
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        try {
            int id = expertGame.getCharacterCards().get(0).getId();
            if(id == 1 || id == 4 || id == 7 || id == 10)
                assertEquals(1, expertGame.getCharacterCards().get(0).getCost());
            else if(id == 2 || id == 5 || id == 8 || id == 11)
                assertEquals(2, expertGame.getCharacterCards().get(0).getCost());
            else assertEquals(3, expertGame.getCharacterCards().get(0).getCost());
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests if CharacterCard 01 has the right number of students on it.
     */
    @Test
    void checkNumberOfStudentsOnCharacterCard01(){
        createACharacterCard(new CharacterCard(1,1,0, expertGame.getBag()));
        assertEquals(4, studentsOnCharacterCard());
    }

    /**
     * Tests if CharacterCard 07 has the right number of students on it.
     */
    @Test
    void checkNumberOfStudentsOnCharacterCard07(){
        createACharacterCard(new CharacterCard(7,1,0, expertGame.getBag()));
        assertEquals(6, studentsOnCharacterCard());
    }

    /**
     * Tests if CharacterCard 11 has the right number of students on it.
     */
    @Test
    void checkNumberOfStudentsOnCharacterCard11(){
        createACharacterCard(new CharacterCard(11,2,0, expertGame.getBag()));
        assertEquals(4, studentsOnCharacterCard());
    }


    /**
     * Tests if the method useCharacterCard throws an exception when the player has already played a character card.
     */
    @Test
    void alreadyPlayedACharacterCard(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        setUpCharacterCards(new CharacterCard(2, 2, 0,expertGame.getBag()), p1,4);
        try {
            expertGame.getCharacterCards().add(new CharacterCard(8, 2, 0, expertGame.getBag()));
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        try {
            expertGame.setGameState(GameState.ACTION_PHASE);
            expertGame.useCharacterCard(p1, 8);
        } catch (IllegalClientInputException | CharacterCardWithParametersException e) {
            e.printStackTrace();
        }
        assertThrows(IllegalClientInputException.class, () -> expertGame.useCharacterCard(p1, 2));
    }

    /**
     * Tests if the method useCharacterCard update the player's wallet.
     */
    @Test
    void playCharacterCard(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        setUpCharacterCards(new CharacterCard(2, 2, 0, expertGame.getBag()),p1,2);
        try {
            expertGame.useCharacterCard(p1,2);
        } catch (IllegalClientInputException | CharacterCardWithParametersException e) {
            e.printStackTrace();
        }
        assertEquals(0, p1.getWallet().getValue());
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the character card has parameters.
     */
    @Test
    void playCharacterCard01WithParameters(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        setUpCharacterCards(new CharacterCard(1,1,0, expertGame.getBag()), p1,1);
        assertThrows(CharacterCardWithParametersException.class, () -> expertGame.useCharacterCard(p1,1));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the character card has parameters.
     */
    @Test
    void playCharacterCard03WithParameters(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        setUpCharacterCards(new CharacterCard(3,3,0, expertGame.getBag()), p1,3);
        assertThrows(CharacterCardWithParametersException.class, () -> expertGame.useCharacterCard(p1,3));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the character card has parameters.
     */
    @Test
    void playCharacterCard05WithParameters(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        setUpCharacterCards(new CharacterCard(5,2,0, expertGame.getBag()), p1,2);
        assertThrows(CharacterCardWithParametersException.class, () -> expertGame.useCharacterCard(p1,5));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the character card has parameters.
     */
    @Test
    void playCharacterCard07WithParameters(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        p1.setSchoolBoard(TowerColor.WHITE,3);
        p1.getSchoolBoard().getEntrance()[0] = 3;
        p1.getSchoolBoard().getDiningRoom()[1] = 3;
        setUpCharacterCards(new CharacterCard(7,1,0, expertGame.getBag()), p1,1);
        assertThrows(CharacterCardWithParametersException.class, () -> expertGame.useCharacterCard(p1,7));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the character card has parameters.
     */
    @Test
    void playCharacterCard09WithParameters(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        setUpCharacterCards(new CharacterCard(9,3,0, expertGame.getBag()), p1,3);
        assertThrows(CharacterCardWithParametersException.class, () -> expertGame.useCharacterCard(p1,9));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the character card has parameters.
     */
    @Test
    void playCharacterCard10WithParameters(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        p1.setSchoolBoard(TowerColor.WHITE,3);
        p1.getSchoolBoard().getEntrance()[0] = 2;
        p1.getSchoolBoard().getDiningRoom()[1] = 2;
        setUpCharacterCards(new CharacterCard(10,1,0, expertGame.getBag()), p1,1);
        assertThrows(CharacterCardWithParametersException.class, () -> expertGame.useCharacterCard(p1,10));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the character card has parameters.
     */
    @Test
    void playCharacterCard11WithParameters(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        setUpCharacterCards(new CharacterCard(11,2,0, expertGame.getBag()), p1,2);
        assertThrows(CharacterCardWithParametersException.class, () -> expertGame.useCharacterCard(p1,11));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the character card has parameters.
     */
    @Test
    void playCharacterCard12WithParameters(){
        expertGame.setGameState(GameState.ACTION_PHASE);
        setUpCharacterCards(new CharacterCard(12,3,0, expertGame.getBag()), p1,3);
        assertThrows(CharacterCardWithParametersException.class, () -> expertGame.useCharacterCard(p1,12));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the player
     * wants to play a character card in a phase which is not the action phase.
     */
    @Test
    void playCharacterCardWhenNotActionPhase(){
        expertGame.setGameState(GameState.PLANNING_PHASE);
        setUpCharacterCards(new CharacterCard(3, 3, 0, expertGame.getBag()), p1, 3);
        assertThrows(IllegalClientInputException.class, () -> expertGame.useCharacterCard(p1, 3));
    }

    /**
     * Tests if the method useCharacterCard throws an exception when the player
     * wants to apply a character card effect in a phase which is not the action phase.
     */
    @Test
    void applyCharacterCardEffectWhenNotActionPhase(){
        expertGame.setGameState(GameState.PLANNING_PHASE);
        setUpCharacterCards(new CharacterCard(2, 2, 0, expertGame.getBag()), p1, 3);
        assertThrows(IllegalClientInputException.class, () -> expertGame.applyCharacterCardEffect(2, new CardEffect02(new ProfessorsManager())));
    }

    private int studentsOnCharacterCard(){
        int cont = 0;
        int [] array = new int[5];
        try {
            array = expertGame.getCharacterCards().get(0).getStudents();
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
        for (int j : array) {
            cont += j;
        }
        return cont;
    }

    private void increaseWalletByValue(Player p, int value){
        for (int i = 0; i < value; i++){
            p.getWallet().increaseValue();
        }
    }

    private void createACharacterCard(CharacterCard characterCard){
        try {
            expertGame.getCharacterCards().add(characterCard);
        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }
    }

    private void setUpCharacterCards(CharacterCard characterCard, Player p, int walletValue){
        expertGame.setCurrentPlayer(p);
        increaseWalletByValue(p, walletValue);
        try {
            expertGame.getCharacterCards().add(characterCard);

        } catch (IllegalClientInputException e) {
            e.printStackTrace();
        }

    }

}