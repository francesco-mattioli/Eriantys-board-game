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

class CardEffect11Test {

    private Player player;
    private CharacterCard characterCard;
    private Bag bag;

    @BeforeEach
    void setUp(){
        bag = new Bag();
        bag.fillBag();
        player = new Player("TestPlayer");
        player.setSchoolBoard(TowerColor.BLACK, 2);
        characterCard = new CharacterCard(11,0,0,bag);
        characterCard.getStudents()[Color.BLUE.ordinal()] = 1;
        characterCard.getStudents()[Color.GREEN.ordinal()] = 0;
    }

    @AfterEach
    void tearDown(){
        bag = null;
        player = null;
        characterCard = null;
    }

    /**
     * This test checks if the effect launches an exception when the player wants to move a student which
     * is not on the character card.
     */
    @Test
    void addStudentWithColorNotOnTheCard(){
        assertThrows(IllegalClientInputException.class, ()->characterCard.executeEffect(new CardEffect11(Color.GREEN, player.getSchoolBoard(),bag,characterCard)));
    }

    /**
     * This test checks if the effect adds only one student when the entrance has zero.
     */
    @Test
    void addStudentWhenDiningRoomHasZero(){
        try {
            characterCard.executeEffect(new CardEffect11(Color.BLUE, player.getSchoolBoard(),bag,characterCard));
        } catch (EndGameException | IllegalClientInputException e) {
            e.printStackTrace();
        }
        assertEquals(1,player.getSchoolBoard().getDiningRoom()[Color.BLUE.ordinal()]);
    }
}