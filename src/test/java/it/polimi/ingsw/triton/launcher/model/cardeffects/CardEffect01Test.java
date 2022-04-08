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


    @Test
    public void addStudentIntoIslandWhenHasZero(){
        characterCard.executeEffect(new CardEffect01(characterCard, Color.BLUE, island, bag));
        assertEquals(1,island.getStudents()[Color.BLUE.ordinal()]);
    }


}