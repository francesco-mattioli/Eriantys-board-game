package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
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
     * This test checks if the effect adds only one student when the island has zero.
     * We call the helper method aStudentOnTheCard() in order to execute the test
     * drawing a student that is actually on the card.
     */
    @Test
    public void addStudentIntoIslandWhenHasZeroStudent(){
        Color studentColorToDraw = aStudentOnTheCard(characterCard);
        assert studentColorToDraw != null;
        characterCard.executeEffect(new CardEffect01(characterCard, studentColorToDraw, island, bag));
        assertEquals(1,island.getStudents()[studentColorToDraw.ordinal()]);
    }

    /**
     * This is a helper method for addStudentIntoIslandWhenHasZeroStudents
     * @param card on which identify a student present on the card
     * @return the student Color of a student present on the card
     */
    public Color aStudentOnTheCard(CharacterCard card){
        for(int i=0;i<characterCard.getStudents().length;i++){
            if(characterCard.getStudents()[i]!=0){
                return Color.values()[characterCard.getStudents()[i]];
            }
        }
        return null;
    }




}