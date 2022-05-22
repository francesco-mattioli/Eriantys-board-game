package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    private Bag bag;

    @BeforeEach
    void setUp() {
        bag = new Bag();
    }

    @AfterEach
    void tearDown() {
        bag = null;
    }

    /**
     * Tests if the method throws an exception when the bag is empty.
     */
    @Test
    void throwsNoSuchElementExceptionIfEmpty() {
        assertThrows(NoSuchElementException.class, bag::drawStudent);
    }

    /**
     * Tests if the number of students is correct after drawing a student.
     */
    @Test
    void drawOneStudentWhenTheBagHasTwo() {
        int current = 0;
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.drawStudent();
        for (int i = 0; i < bag.getStudents().length; i++) {
            current += bag.getStudents()[i];
        }
        assertEquals(1, current);
    }


    /**
     * Tests if the number of students is correct after drawing a student.
     */
    @Test
    void drawOneStudentWhenTheBagHasOne() {
        int current = 0;
        bag.addStudent(Color.BLUE);
        bag.drawStudent();
        for (int i = 0; i < bag.getStudents().length; i++) {
            current += bag.getStudents()[i];
        }
        assertEquals(0, current);
    }


    /**
     * Tests if the color drawn is correct.
     */
    @Test
    void drawCorrectStudentColor() {
        bag.addStudent(Color.BLUE);
        bag.drawStudent();
        assertEquals(0, bag.getStudents()[Color.BLUE.ordinal()]);
    }

    /**
     * Tests if the bag is correctly filled.
     */
    @Test
    void fillBag() {
        bag.fillBag();
        int tot = 0;
        for (int i = 0; i < Color.values().length; i++) {
            tot += bag.getStudents()[i];
        }
        assertEquals(120, tot);
    }

    /**
     * Tests if the method throws an exception if the student color is null.
     */
    @Test
    void addNullStudentColor(){
        assertThrows(IllegalArgumentException.class, () -> bag.addStudent(null));
    }

    /**
     * Tests if after the function add the number of students is correct
     */
    @Test
    void addStudentWhenBagEmpty() {
        //Bag bag = Bag.instance(2);
        int current = 0;
        bag.addStudent(Color.BLUE);
        for (int i = 0; i < bag.getStudents().length; i++) {
            current += bag.getStudents()[i];
        }
        assertEquals(1, current);
    }

    /**
     * Tests if the bag is empty
     */
    @Test
    void theBagIsEmpty() {
        //Bag bag = Bag.instance(2);
        assertTrue(bag.isEmpty());
    }

    /**
     * Tests if the bag is not empty
     */
    @Test
    void theBagIsNotEmpty() {
        //Bag bag = Bag.instance(2);
        bag.addStudent(Color.BLUE);
        assertFalse(bag.isEmpty());
    }

}