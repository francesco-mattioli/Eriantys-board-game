package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect10Test {
    private CharacterCard characterCard;
    private Player player;
    private Player secondPlayer;
    private List<Player> players;
    private Bag bag;
    private int[] fromDiningRoom;
    private int[] fromEntrance;
    private Player[] professors;

    @BeforeEach
    void setUp(){
        bag = new Bag();
        bag.fillBag();
        fromDiningRoom = new int[5];
        fromEntrance = new int[5];
        characterCard = new CharacterCard(10,1,0,bag);
        player = new Player("TestPlayer");
        secondPlayer = new Player("TestPlayer2");
        players = new ArrayList<>();
        players.add(player);
        players.add(secondPlayer);
        player.setSchoolBoard(TowerColor.BLACK, 2);
        player.getSchoolBoard().getEntrance()[Color.BLUE.ordinal()] = 3;
        player.getSchoolBoard().getDiningRoom()[Color.PINK.ordinal()] = 3;
        secondPlayer.setSchoolBoard(TowerColor.WHITE, 2);
        secondPlayer.getSchoolBoard().getEntrance()[Color.BLUE.ordinal()] = 1;
        secondPlayer.getSchoolBoard().getDiningRoom()[Color.PINK.ordinal()] = 1;
        professors = new Player[5];
        professors[Color.BLUE.ordinal()] = null;
        professors[Color.RED.ordinal()] = null;
        professors[Color.GREEN.ordinal()] = null;
        professors[Color.PINK.ordinal()] = null;
        professors[Color.YELLOW.ordinal()] = null;
    }

    @AfterEach
    void tearDown(){
        bag = null;
        characterCard = null;
        player = null;
    }


    /**
     * This test checks if students are swapped correctly from the dining room to the entrance.
     */
    @Test
    void checkIfStudentSwitchedInEntrance(){
        fromDiningRoom[Color.PINK.ordinal()] = 2;
        fromEntrance[Color.BLUE.ordinal()] = 2;
        try {
            characterCard.executeEffect(new CardEffect10(fromEntrance,fromDiningRoom,player, new GeneralCoinSupply(5), new ProfessorsManager(), professors, players));
        } catch (EndGameException | IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,player.getSchoolBoard().getEntrance()[Color.PINK.ordinal()]);
    }

    /**
     * This test throws an exception if entrance hasn't enough students.
     */
    @Test
    void throwExceptionIfNotEnoughStudentsInEntrance(){
        fromDiningRoom[Color.PINK.ordinal()] = 3;
        fromEntrance[Color.BLUE.ordinal()] = 4;
        assertThrows(IllegalClientInputException.class, () -> characterCard.executeEffect(new CardEffect10(fromEntrance,fromDiningRoom,player, new GeneralCoinSupply(5), new ProfessorsManager(), professors, players)));
    }

    /**
     * This test checks if students are swapped correctly from the entrance to the dining room.
     */
    @Test
    void checkIfStudentSwitchedInDiningRoom(){
        fromDiningRoom[Color.PINK.ordinal()] = 2;
        fromEntrance[Color.BLUE.ordinal()] = 2;
        try {
            characterCard.executeEffect(new CardEffect10(fromEntrance,fromDiningRoom,player, new GeneralCoinSupply(5), new ProfessorsManager(), professors, players));
        } catch (EndGameException | IllegalClientInputException | EmptyGeneralCoinSupplyException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);
    }

    /**
     * This test throws an exception if dining room hasn't enough students.
     */
    @Test
    void throwExceptionIfNotEnoughStudentsInDiningRoom(){
        fromDiningRoom[Color.PINK.ordinal()] = 4;
        fromEntrance[Color.BLUE.ordinal()] = 3;
        assertThrows(IllegalClientInputException.class, () -> characterCard.executeEffect(new CardEffect10(fromEntrance,fromDiningRoom,player, new GeneralCoinSupply(5), new ProfessorsManager(), professors, players)));
    }
}