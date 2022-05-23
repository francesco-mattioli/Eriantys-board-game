package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardEffect12Test {

    private Bag bag;
    private ArrayList<Player> players;
    private Player playerTest1;
    private CharacterCard characterCard;

    @BeforeEach
    public void setUp() {
        bag = new Bag();
        bag.fillBag();
        characterCard = new CharacterCard(12, 0, 0, bag);
        playerTest1 = new Player("TestPlayer1");
        playerTest1.setSchoolBoard(TowerColor.BLACK, 2);
        players = new ArrayList<>();
        players.add(playerTest1);
    }


    @AfterEach
    public void tearDown() {
        bag = null;
        playerTest1 = null;
        players = null;
    }


    /**
     * This test check if the effect remove three students from the player's entrance when the entrance has four
     */
    @Test
    void removeThreeStudentsWhenPlayerHasFour() {
        removeThreeStudentsWhenPlayerHasACertainNumber(4);
        assertEquals(1, playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);
    }


    /**
     * This test check if the effect remove two students from the player's entrance when the entrance has two
     */
    @Test
    void removeThreeStudentsWhenPlayerHasTwo() {
        removeThreeStudentsWhenPlayerHasACertainNumber(2);
        assertEquals(0, playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);

    }

    /**
     * This test check if the effect remove zero students from the player's entrance when the entrance has zero
     */
    @Test
    void removeThreeStudentsWhenPlayerHasZero() {
        removeThreeStudentsWhenPlayerHasACertainNumber(0);
        assertEquals(0, playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);

    }

    void removeThreeStudentsWhenPlayerHasACertainNumber(int certainNumber) {
        playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()] = certainNumber;
        try {
            characterCard.executeEffect(new CardEffect12(Color.BLUE, players, bag));
        } catch (EndGameException | IllegalClientInputException e) {
            e.printStackTrace();
        }
    }


}