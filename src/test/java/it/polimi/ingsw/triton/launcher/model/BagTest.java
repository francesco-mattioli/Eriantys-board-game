package it.polimi.ingsw.triton.launcher.model;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    void drawStudent() {
        /*int before = 0;
        int after = 0;
        Bag bag = Bag.instance(2);
        //assertThrows(NoSuchElementException.class, bag::isEmpty);
        bag.fillBag();
            for (int i = 0; i<5; i++){
                before+=bag.getStudents()[i];
            }
            drawStudent();
            for (int i = 0; i<5; i++){
                after+=bag.getStudents()[i];
            }
            assertEquals(after,before-1);*/
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