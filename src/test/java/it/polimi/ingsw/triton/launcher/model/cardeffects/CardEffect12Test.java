package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.Player;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect12Test {

    private Bag bag;
    private ArrayList<Player> players;
    private Player playerTest1;
    private CharacterCard characterCard;

    @BeforeEach
    public void setUp(){
        bag = new Bag(1);
        bag.fillBag();
        characterCard = new CharacterCard(12,0,0,bag);
        playerTest1 = new Player("TestPlayer1");
        playerTest1.setSchoolBoard(TowerColor.BLACK);
        players = new ArrayList<>();
        players.add(playerTest1);
    }


    @AfterEach
    public void tearDown(){
        bag = null;
        playerTest1 = null;
        players = null;
    }


    @Test
    public void removeThreeStudentsWhenPlayerHasFour(){
        playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()] = 4;
        characterCard.executeEffect(new CardEffect12(Color.BLUE,players,bag));
        assertEquals(1,playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);

    }


    @Test
    public void removeThreeStudentsWhenPlayerHasTwo(){
        playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()] = 2;
        characterCard.executeEffect(new CardEffect12(Color.BLUE,players,bag));
        assertEquals(0,playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);

    }

    @Test
    public void removeThreeStudentsWhenPlayerHasZero(){
        playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()] = 0;
        characterCard.executeEffect(new CardEffect12(Color.BLUE,players,bag));
        assertEquals(0,playerTest1.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);

    }


}