package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect11Test {

    private Player player;
    private CharacterCard characterCard;
    private Bag bag;

    @BeforeEach
    public void setUp(){
        bag = new Bag(2);
        bag.fillBag();
        player = new Player("TestPlayer");
        player.setSchoolBoard(TowerColor.BLACK);
        characterCard = new CharacterCard(11,0,0,bag);
        characterCard.getStudents()[Color.BLUE.ordinal()] = 1;
    }

    @AfterEach
    public void tearDown(){
        bag = null;
        player = null;
        characterCard = null;
    }


    @Test
    public void addStudentWhenDiningRoomHasZero(){
       characterCard.executeEffect(new CardEffect11(Color.BLUE, player.getSchoolBoard(),bag,characterCard));
       assertEquals(1,player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);
    }

}