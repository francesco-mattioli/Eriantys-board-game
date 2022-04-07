package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    private Bag bag;
    private CharacterCard card;

    @BeforeEach
    void setUp(){
        bag = new Bag(2);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        card = new CharacterCard(1,1,3,bag);
    }

    @AfterEach
    void tearDown(){
        bag=null;
        card=null;
    }

    @Test
    void throwsIllegalArgumentExceptionWhenDrawingNull(){
        assertThrows(IllegalArgumentException.class,()->{card.drawStudent(null);});
    }

    @Test
    void throwsIllegalArgumentExceptionWhenStudentNotPresent(){
        //Green is not present on the card
        assertThrows(IllegalArgumentException.class,()->{card.drawStudent(Color.GREEN);});
    }

    @Test
    void costIsOneUnitGraterAfterIncrease() {
        card.increaseCost();
        assertEquals(2,card.getCost());
    }


    @Test
    void numberOfStudentsIsOneSmallerAfterDraw() {
        Color color=card.drawStudent(Color.BLUE);
        int[] students = card.getStudents();
        int sum=0;
        for (int student : students) {
            sum += student;
        }
        assertEquals(3,sum);
    }
}