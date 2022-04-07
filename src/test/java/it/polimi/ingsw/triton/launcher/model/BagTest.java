package it.polimi.ingsw.triton.launcher.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    private Bag bag;

    @BeforeEach
    void setUp() {
        bag = new Bag(2);
    }

    @AfterEach
    void tearDown() {
        bag = null;
    }

    /**
     * Test if the bag is empty throws an exception
     */
    @Test
    void throwsNoSuchElementExceptionIfEmpty() {
        //Bag bag = Bag.instance(2);
        assertThrows(NoSuchElementException.class, bag::drawStudent);
    }

    /**
     * Test if after the function draw the number of students is correct
     */
    @Test
    void drawOneStudentWhenTheBagHasTwo() {
        //Bag bag = Bag.instance(2);
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
     * Test if after the function draw the number of students is correct
     */
    @Test
    void drawOneStudentWhenTheBagHasOne() {
        //Bag bag = Bag.instance(2);
        int current = 0;
        bag.addStudent(Color.BLUE);
        bag.drawStudent();
        for (int i = 0; i < bag.getStudents().length; i++) {
            current += bag.getStudents()[i];
        }
        assertEquals(0, current);
    }


    /**
     * Test if after the function draw the correct student color
     */
    @Test
    void drawCorrectStudentColor() {
        //Bag bag = Bag.instance(2);
        int current = 0;
        bag.addStudent(Color.BLUE);
        bag.drawStudent();
        current = bag.getStudents()[Color.BLUE.ordinal()];
        assertEquals(0, current);
    }

    /**
     * Test if when there are two players the cloudtile has 3 students
     */
    @Test
    void fillCloudTileWithTwoPlayers() {
        CloudTile cloudTile = new CloudTile(1);
        //Bag bag = Bag.instance(2);
        bag.fillBag();
        int current = 0;
        bag.fillCloudTile(cloudTile);
        for (int i = 0; i < cloudTile.getStudents().length; i++) {
            current += cloudTile.getStudents()[i];
        }
        assertEquals(3, current);
    }

    /**
     * Test if when there are three players the cloudtile has 4 students
     */
    @Test
    void fillCloudTileWithThreePlayers() {
        CloudTile cloudTile = new CloudTile(1);
        Bag bagForThree = new Bag(3);
        bagForThree.fillBag();
        int current = 0;
        bagForThree.fillCloudTile(cloudTile);
        for (int i = 0; i < cloudTile.getStudents().length; i++) {
            current += cloudTile.getStudents()[i];
        }
        assertEquals(4, current);
    }


    /**
     * Test if bag  his contains 120 (24 students for each color) students after fillbag()
     */
    @Test
    void fillBag() {
        //Bag bag = Bag.instance(2);
        bag.fillBag();
        int tot = 0;
        for (int i = 0; i < Color.values().length; i++) {
            tot += bag.getStudents()[i];
        }
        assertEquals(120, tot);
    }

    /**
     * Test if after the function add the number of students is correct
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
     * Test if the bag is empty
     */
    @Test
    void theBagIsEmpty() {
        //Bag bag = Bag.instance(2);
        assertTrue(bag.isEmpty());
    }

    /**
     * Test if the bag is not empty
     */
    @Test
    void theBagIsNotEmpty() {
        //Bag bag = Bag.instance(2);
        bag.addStudent(Color.BLUE);
        assertFalse(bag.isEmpty());
    }

}