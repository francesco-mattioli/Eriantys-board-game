package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {

    @Test
    void moveTowerOntoIsland() {
        // TODO implement here
    }

    /**
     * Test if the tower is added on the school board
     */
    @Test
    void moveTowerOntoSchoolBoard() {
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        assertEquals(8, schoolBoard.getNumTowers());
        //it's not finished
    }

    /**
     * Test if the student is added in the dining room of the school board
     */
    @Test
    void addStudentIntoDiningRoom() {
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        assertEquals(0, schoolBoard.getStudentsNumber(Color.BLUE));
        schoolBoard.addStudentIntoDiningRoom(Color.GREEN);
        assertEquals(0, schoolBoard.getStudentsNumber(Color.BLUE));
        assertEquals(1, schoolBoard.getStudentsNumber(Color.GREEN));
    }

    /**
     * Test if the student is added in the entrance of the school board
     */
    @Test
    void addStudentIntoEntrance() {
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        assertEquals(0, schoolBoard.getEntrance()[Color.BLUE.ordinal()]);
        schoolBoard.addStudentIntoEntrance(Color.GREEN);
        assertEquals(0, schoolBoard.getEntrance()[Color.BLUE.ordinal()]);
        assertEquals(1, schoolBoard.getEntrance()[Color.GREEN.ordinal()]);
    }

    /**
     * Return the number of students of a certain color
     * in the dining room of the school board
     */
    @Test
    void getStudentsNumber() {
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        assertEquals(0, schoolBoard.getStudentsNumber(Color.GREEN));
        schoolBoard.addStudentIntoDiningRoom(Color.GREEN);
        assertEquals(1, schoolBoard.getStudentsNumber(Color.GREEN));
    }

    /**
     * Test if the student is removed from the entrance of the school board
     */
    @Test
    void removeStudentFromEntrance() {
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        Color student = Color.BLUE;
        Exception exception = Assertions.assertThrows(RuntimeException.class, ()->schoolBoard.removeStudentFromEntrance(student));
        assertEquals("No students of color " + student.name() + " at entrance", exception.getMessage());
        schoolBoard.addStudentIntoEntrance(Color.GREEN);
        schoolBoard.addStudentIntoEntrance(student);
        schoolBoard.removeStudentFromEntrance(Color.GREEN);
        assertEquals(1, schoolBoard.getEntrance()[student.ordinal()]);
        schoolBoard.removeStudentFromEntrance(student);
        assertEquals(0, schoolBoard.getEntrance()[student.ordinal()]);
    }
}