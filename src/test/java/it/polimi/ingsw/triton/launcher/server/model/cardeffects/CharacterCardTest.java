package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Bag;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    private Bag bag;
    private CharacterCard card;

    /**
     * This method sets up the bag for the test
     */
    @BeforeEach
    void setUp(){
        bag = new Bag();
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        card = new CharacterCard(1,1,3,bag);
    }

    /**
     * For clarity, after each test, it sets the bag and the card at null.
     * This is not necessary.
     */
    @AfterEach
    void tearDown(){
        bag=null;
        card=null;
    }

    /**
     * If a null student is passed, an exception must be thrown
     */
    @Test
    void throwsIllegalArgumentExceptionWhenDrawingNull(){
        assertThrows(IllegalClientInputException.class,()->{card.drawStudent(null);});
    }


    /**
     * It throws an exception if drawStudent() is called with a student color not present on the card.
     */
    @Test
    void throwsIllegalArgumentExceptionWhenStudentNotPresent(){
        //Green is not present on the card
        assertThrows(IllegalClientInputException.class,()->{card.drawStudent(Color.GREEN);});
    }

    /**
     * Checks if the cost is increased after calling increaseCost()
     */
    @Test
    void costIsOneUnitGraterAfterIncrease() {
        card.increaseCost();
        assertEquals(2,card.getCost());
    }

    /**
     * Checks if the number of students is decreased after drawing a student from the card.
     */
    @Test
    void numberOfStudentsIsOneSmallerAfterDraw() {
        try {
            Color color=card.drawStudent(Color.BLUE);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        int[] students = card.getStudents();
        int sum=0;
        for (int student : students) {
            sum += student;
        }
        assertEquals(3,sum);
    }
}