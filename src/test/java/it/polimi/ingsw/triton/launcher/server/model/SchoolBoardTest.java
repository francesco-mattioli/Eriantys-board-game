package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {
    private SchoolBoard schoolBoard;
    private SchoolBoard threePlayersSchoolBoard;

    @BeforeEach
    void setup(){
        schoolBoard = new SchoolBoard(TowerColor.BLACK, 2);
        threePlayersSchoolBoard = new SchoolBoard(TowerColor.BLACK, 3);

    }

    @AfterEach
    void tearDown(){
        schoolBoard = null;
        threePlayersSchoolBoard = null;
    }

    /**
     * Tests if the number of towers in the school board is decreased when
     * the player moves them onto island.
     */
    @Test
    void testMoveTowerOntoIsland() {
        int numTowers = schoolBoard.getNumTowers();
        schoolBoard.moveTowerOntoIsland(2);
        assertEquals(numTowers - 2, schoolBoard.getNumTowers());
    }

    /**
     * Tests if the tower is added on the school board.
     */
    @Test
    void testMoveTowerOntoSchoolBoard() {
        int movedTowers = 3;
        schoolBoard.moveTowerOntoIsland(movedTowers);
        int numTowersSchoolBoard = schoolBoard.getNumTowers();
        schoolBoard.moveTowerOntoSchoolBoard(movedTowers);
        assertEquals(numTowersSchoolBoard + movedTowers, schoolBoard.getNumTowers());
    }

    /**
     * Tests if the student is added in the dining room of the school board.
     */
    @Test
    void testAddStudentIntoDiningRoom() {
        int numGreenStudents = schoolBoard.getStudentsNumber(Color.BLUE);
        schoolBoard.addStudentIntoDiningRoom(Color.GREEN);
        assertEquals(0, schoolBoard.getStudentsNumber(Color.BLUE));
        assertEquals(numGreenStudents + 1, schoolBoard.getStudentsNumber(Color.GREEN));
    }

    /**
     * Tests if the student is added in the entrance of the school board.
     */
    @Test
    void testAddStudentIntoEntrance() {
        int numGreenStudentsEntrance = schoolBoard.getEntrance()[Color.GREEN.ordinal()];
        schoolBoard.addStudentIntoEntrance(Color.GREEN);
        assertEquals(0, schoolBoard.getEntrance()[Color.BLUE.ordinal()]);
        assertEquals(numGreenStudentsEntrance + 1, schoolBoard.getEntrance()[Color.GREEN.ordinal()]);
    }

    /**
     * Returns the number of students of a certain color
     * in the dining room of the school board.
     */
    @Test
    void getStudentsNumber() {
        assertEquals(0, schoolBoard.getStudentsNumber(Color.GREEN));
        schoolBoard.addStudentIntoDiningRoom(Color.GREEN);
        assertEquals(1, schoolBoard.getStudentsNumber(Color.GREEN));
    }

    /**
     * Tests if the student is removed when there are not students of the color chosen by the player.
     */
    @Test
    void testRemoveWhenThereAreNoStudents() {
        Color student = Color.BLUE;
        assertThrows(IllegalArgumentException.class, ()->schoolBoard.removeStudentFromEntrance(student));
    }

    /**
     * Tests if the student is removed from the entrance when there are students of the color chosen by the player.
     */
    @Test
    void testRemoveWhenThereAreStudents() {
        Color student = Color.BLUE;
        schoolBoard.addStudentIntoEntrance(Color.GREEN);
        schoolBoard.addStudentIntoEntrance(student);
        int blueStudentsEntrance = schoolBoard.getEntrance()[student.ordinal()];
        schoolBoard.removeStudentFromEntrance(student);
        assertEquals(blueStudentsEntrance - 1, schoolBoard.getEntrance()[student.ordinal()]);
    }

    /**
     * Tests if the initial number of towers is correct at the start of the game.
     */
    @Test
    void testCorrectInitialNumTowers(){
        assertEquals(8, schoolBoard.getNumTowers());
        assertEquals(6, threePlayersSchoolBoard.getNumTowers());
    }
}