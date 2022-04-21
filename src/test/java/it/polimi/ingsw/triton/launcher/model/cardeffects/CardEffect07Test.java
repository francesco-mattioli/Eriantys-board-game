package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
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
    public void setUp(){
        bag = new Bag(2);
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
    public void tearDown(){
        bag = null;
        characterCard = null;
        player = null;
    }

    /**
     * This test checks if students are swapped correctly from the card to the entrance
     */
    @Test
    public void checkIfStudentSwitchedInEntrance(){
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        characterCard.executeEffect(new CardEffect07(characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));
        assertEquals(3,player.getSchoolBoard().getEntrance()[Color.PINK.ordinal()]);
    }

    /**
     * This test throws an exception if entrance hasn't enough students
     */
    @Test
    public void throwExceptionIfNotEnoughStudentsInEntrance(){
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 4;
        assertThrows(RuntimeException.class, () -> {characterCard.executeEffect(new CardEffect07(characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));});
    }

    /**
     * This test checks if students are swapped correctly from the entrance to the card
     */
    @Test
    public void checkIfStudentSwitchedInCharacterCard(){
        int previousBlueOnCard;
        previousBlueOnCard = characterCard.getStudents()[Color.BLUE.ordinal()];
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        characterCard.executeEffect(new CardEffect07(characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));
        assertEquals(previousBlueOnCard + 3,characterCard.getStudents()[Color.BLUE.ordinal()]);
    }

    /**
     * This test throws an exception if card hasn't enough students
     */
    @Test
    public void throwExceptionIfNotEnoughStudentsOnCharacterCard(){
        fromCard[Color.PINK.ordinal()] = 4;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        assertThrows(RuntimeException.class, () -> {characterCard.executeEffect(new CardEffect07(characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));});
    }

}