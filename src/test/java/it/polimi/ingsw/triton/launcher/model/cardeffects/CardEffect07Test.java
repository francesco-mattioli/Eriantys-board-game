package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.Player;
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
        player.setSchoolBoard(TowerColor.BLACK);
        player.getSchoolBoard().getEntrance()[Color.BLUE.ordinal()] = 3;
    }

    @AfterEach
    public void tearDown(){
        bag = null;
        characterCard = null;
        player = null;
    }

    @Test
    public void checkIfStudentSwitchedInEntrance(){
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        characterCard.executeEffect(new CardEffect07(characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));
        assertEquals(3,player.getSchoolBoard().getEntrance()[Color.PINK.ordinal()]);
    }

    @Test
    public void throwExceptionIfNotEnoughStudentsInEntrance(){
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 4;
        assertThrows(RuntimeException.class, () -> {characterCard.executeEffect(new CardEffect07(characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));});
    }

    @Test
    public void checkIfStudentSwitchedInCharacterCard(){
        fromCard[Color.PINK.ordinal()] = 3;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        characterCard.executeEffect(new CardEffect07(characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));
        assertEquals(3,characterCard.getStudents()[Color.BLUE.ordinal()]);
    }

    @Test
    public void throwExceptionIfNotEnoughStudentsOnCharacterCard(){
        fromCard[Color.PINK.ordinal()] = 4;
        fromSchoolBoard[Color.BLUE.ordinal()] = 3;
        assertThrows(RuntimeException.class, () -> {characterCard.executeEffect(new CardEffect07(characterCard.getStudents(), fromCard, fromSchoolBoard, player.getSchoolBoard()));});
    }

}