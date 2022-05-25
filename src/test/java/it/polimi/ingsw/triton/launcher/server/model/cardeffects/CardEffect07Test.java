package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect07Test {

    private CharacterCard characterCard;
    private Player player;
    private Bag bag;
    private int[] fromCard;
    private int[] fromSchoolBoard;

    @BeforeEach
    void setUp(){
        bag = new Bag();
        bag.fillBag();
        fromCard = new int[5];
        fromSchoolBoard = new int[5];
        characterCard = new CharacterCard(7,0,0,bag);
        characterCard.getStudents()[Color.PINK.ordinal()] = 3;
        player = new Player("TestPlayer");
        player.setSchoolBoard(TowerColor.BLACK, 2);
        player.getSchoolBoard().getEntrance()[Color.BLUE.ordinal()] = 3;
    }

    @AfterEach
    void tearDown(){
        bag = null;
        characterCard = null;
        player = null;
    }

    /**
     * This test checks if students are swapped correctly from the card to the entrance.
     */
    @Test
    void checkIfStudentSwitchedInEntrance(){
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        try {
            characterCard.executeEffect(new CardEffect07(characterCard,characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));
        } catch (EndGameException | IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,player.getSchoolBoard().getEntrance()[Color.PINK.ordinal()]);
    }

    /**
     * This test throws an exception if entrance hasn't enough students.
     */
    @Test
    void throwExceptionIfNotEnoughStudentsInEntrance(){
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 4;
        assertThrows(IllegalClientInputException.class, () -> characterCard.executeEffect(new CardEffect07(characterCard, characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard())));
    }

    /**
     * This test checks if students are swapped correctly from the entrance to the card.
     */
    @Test
    void checkIfStudentSwitchedInCharacterCard(){
        int previousBlueOnCard;
        previousBlueOnCard = characterCard.getStudents()[Color.BLUE.ordinal()];
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        try {
            characterCard.executeEffect(new CardEffect07(characterCard, characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));
        } catch (EndGameException | IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(previousBlueOnCard + 3,characterCard.getStudents()[Color.BLUE.ordinal()]);
    }

    /**
     * This test throws an exception if card hasn't enough students.
     */
    @Test
    void throwExceptionIfNotEnoughStudentsOnCharacterCard(){
        fromCard[Color.PINK.ordinal()] = 4;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        assertThrows(IllegalClientInputException.class, () -> characterCard.executeEffect(new CardEffect07(characterCard, characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard())));
    }
}