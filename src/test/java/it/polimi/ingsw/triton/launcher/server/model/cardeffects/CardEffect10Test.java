package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect10Test {
    private CharacterCard characterCard;
    private Player player;
    private Bag bag;
    private int[] fromDiningRoom;
    private int[] fromEntrance;

    @BeforeEach
    public void setUp(){
        bag = new Bag(2);
        bag.fillBag();
        fromDiningRoom = new int[5];
        fromEntrance = new int[5];
        characterCard = new CharacterCard(7,0,0,bag);
        player = new Player("TestPlayer");
        player.setSchoolBoard(TowerColor.BLACK, 2);
        player.getSchoolBoard().getEntrance()[Color.BLUE.ordinal()] = 3;
        player.getSchoolBoard().getDiningRoom()[Color.PINK.ordinal()] = 3;
    }

    @AfterEach
    public void tearDown(){
        bag = null;
        characterCard = null;
        player = null;
    }


    /**
     * This test checks if students are swapped correctly from the dining room to the entrance
     */
    @Test
    public void checkIfStudentSwitchedInEntrance(){
        fromDiningRoom[Color.PINK.ordinal()] = 3;
        fromEntrance[Color.BLUE.ordinal()] = 3;
        try {
            characterCard.executeEffect(new CardEffect10(fromEntrance,fromDiningRoom,player.getSchoolBoard()));
        } catch (EndGameException | IllegalClientInputException e) {
            e.printStackTrace();
        }
        assertEquals(3,player.getSchoolBoard().getEntrance()[Color.PINK.ordinal()]);
    }

    /**
     * This test throws an exception if entrance hasn't enough students
     */
    @Test
    public void throwExceptionIfNotEnoughStudentsInEntrance(){
        fromDiningRoom[Color.PINK.ordinal()] = 3;
        fromEntrance[Color.BLUE.ordinal()] = 4;
        assertThrows(RuntimeException.class, () -> {characterCard.executeEffect(new CardEffect10(fromEntrance,fromDiningRoom,player.getSchoolBoard()));});
    }

    /**
     * This test checks if students are swapped correctly from the entrance to the dining room
     */
    @Test
    public void checkIfStudentSwitchedInDiningRoom(){
        fromDiningRoom[Color.PINK.ordinal()] = 3;
        fromEntrance[Color.BLUE.ordinal()] = 3;
        try {
            characterCard.executeEffect(new CardEffect10(fromEntrance,fromDiningRoom,player.getSchoolBoard()));
        } catch (EndGameException | IllegalClientInputException e) {
            e.printStackTrace();
        }
        assertEquals(3,player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);
    }

    /**
     * This test throws an exception if dining room hasn't enough students
     */
    @Test
    public void throwExceptionIfNotEnoughStudentsInDiningRoom(){
        fromDiningRoom[Color.PINK.ordinal()] = 4;
        fromEntrance[Color.BLUE.ordinal()] = 3;
        assertThrows(RuntimeException.class, () -> {characterCard.executeEffect(new CardEffect10(fromEntrance,fromDiningRoom,player.getSchoolBoard()));});
    }


}