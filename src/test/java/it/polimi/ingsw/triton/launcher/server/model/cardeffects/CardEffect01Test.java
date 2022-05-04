package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardEffect01Test {

    private Bag bag;
    private Island island;
    private CharacterCard characterCard;



    @BeforeEach
    public void setUp(){
        bag = new Bag(2);
        bag.fillBag();
        island = new Island(1);
        characterCard = new CharacterCard(1,0,0,bag);
    }


    @AfterEach
    public void tearDown(){
        bag = null;
        island = null;
        characterCard = null;
    }

    /**
     * This test checks if the effect launches an exception when the player wants to move a student which
     * is not on the character card.
     */
    @Test
    public void addStudentWhenCardHasNotStudentOfThatColor(){
        characterCard.getStudents()[Color.GREEN.ordinal()] = 0;
        assertThrows(IllegalClientInputException.class, ()->characterCard.executeEffect(new CardEffect01(characterCard, Color.GREEN, island, bag)));
    }

    /**
     * This test checks if the effect adds only one student when the island has zero.
     * We call the helper method aStudentOnTheCard() in order to execute the test
     * drawing a student that is actually on the card.
     */
    @Test
    public void addStudentIntoIslandWhenHasZeroStudent(){
        int studentColorToDraw = aStudentOnTheCard(characterCard);
        assert studentColorToDraw != -1;
        try {
            characterCard.executeEffect(new CardEffect01(characterCard, Color.values()[studentColorToDraw], island, bag));
        } catch (EndGameException | IllegalClientInputException e) {
            e.printStackTrace();
        }
        assertEquals(1,island.getStudents()[studentColorToDraw]);
    }

    /**
     * This is a helper method for addStudentIntoIslandWhenHasZeroStudents.
     * @param card on which identify a student present on the card.
     * @return the student Color of a student present on the card.
     */
    public int aStudentOnTheCard(CharacterCard card){
        for(int i=0;i<card.getStudents().length;i++){
            if(card.getStudents()[i]!=0){
                return i;
            }
        }
        return -1;
    }




}