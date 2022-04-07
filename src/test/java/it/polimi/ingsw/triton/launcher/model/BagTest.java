package it.polimi.ingsw.triton.launcher.model;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    /**
     * Test if the bag is empty throws an exception
     */
    @Test
    void throwsNoSuchElementExceptionIfEmpty() {
        Bag bag = Bag.instance(2);
        assertThrows(NoSuchElementException.class, bag::drawStudent);
    }

    /**
     * Test if after the function draw the number of students is correct
     */
    @Test
    void drawOneStudentWhenTheBagHasTwo(){
        Bag bag = Bag.instance(2);
        int current = 0;
        bag.addStudent(Color.BLUE);
        bag.addStudent(Color.BLUE);
        bag.drawStudent();
        for (int i = 0; i < bag.getStudents().length; i++){
            current += bag.getStudents()[i];
        }
        assertEquals(1,current);
    }


    /**
     * Test if after the function draw the number of students is correct
     */
    @Test
    void drawOneStudentWhenTheBagHasOne(){
        Bag bag = Bag.instance(2);
        int current = 0;
        bag.addStudent(Color.BLUE);
        bag.drawStudent();
        for (int i = 0; i < bag.getStudents().length; i++){
            current += bag.getStudents()[i];
        }
        assertEquals(0,current);
    }


    /**
     * Test if after the function draw the correct student color
     */
    @Test
    void drawCorrectStudentColor(){
        Bag bag = Bag.instance(2);
        int current = 0;
        bag.addStudent(Color.BLUE);
        bag.drawStudent();
        current = bag.getStudents()[Color.BLUE.ordinal()];
        assertEquals(0,current);
    }

    @Test
    void fillCloudTile() {
    }
    /**
     * Test if bag  his contains 120 (24 students for each color) students after fillbag()
     */
    @Test
    void fillBag() {
        Bag bag = Bag.instance(2);
        bag.fillBag();
        int tot = 0;
        for (int i = 0; i < Color.values().length; i++){
            tot+=bag.getStudents()[i];
        }
        assertEquals(120,tot);
    }

    @Test
    void addStudent() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void getStudents() {
    }
}