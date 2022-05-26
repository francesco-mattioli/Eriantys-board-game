package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {
    private SchoolBoard schoolBoard;
    private SchoolBoard threePlayersSchoolBoard;

    @BeforeEach
    void setup() {
        schoolBoard = new SchoolBoard(TowerColor.BLACK, 2);
        threePlayersSchoolBoard = new SchoolBoard(TowerColor.BLACK, 3);
    }

    @AfterEach
    void tearDown() {
        schoolBoard = null;
        threePlayersSchoolBoard = null;
    }

    /**
     * Tests if the number of towers in the school board is decreased when
     * the player moves them onto island and they aren't the last ones.
     */
    @Test
    void testMoveTowerOntoIslandWhenThereAreMore() {
        int numTowers = schoolBoard.getNumTowers();
        try {
            schoolBoard.moveTowerOntoIsland(2);
        } catch (EndGameException e) {
            e.printStackTrace();
        }
        assertEquals(numTowers - 2, schoolBoard.getNumTowers());
    }

    /**
     * Tests if the method launches an exception when the player moves the last tower.
     */
    @Test
    void testMoveTowerOntoIslandWhenTheyAreTheLast() {
        for (int i = 0; i < 7; i++) {
            try {
                schoolBoard.moveTowerOntoIsland(1);
            } catch (EndGameException e) {
                e.printStackTrace();
            }
        }
        assertThrows(EndGameException.class, () -> schoolBoard.moveTowerOntoIsland(1));
    }

    /**
     * Tests if the tower is added on the school board.
     */
    @Test
    void testMoveTowerOntoSchoolBoard() {
        try {
            schoolBoard.moveTowerOntoIsland(4);
        } catch (EndGameException e) {
            e.printStackTrace();
        }
        int numTowersSchoolBoard = schoolBoard.getNumTowers();
        schoolBoard.moveTowerOntoSchoolBoard(2);
        assertEquals(numTowersSchoolBoard + 2, schoolBoard.getNumTowers());
    }

    /**
     * Tests if the student is added in the dining room of the school board.
     */
    @Test
    void testAddStudentIntoDiningRoom() {
        int numGreenStudents = schoolBoard.getStudentsNumber(Color.GREEN);
        try {
            schoolBoard.addStudentIntoDiningRoom(Color.GREEN);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, schoolBoard.getStudentsNumber(Color.BLUE));
        assertEquals(numGreenStudents + 1, schoolBoard.getStudentsNumber(Color.GREEN));
    }

    /**
     * Tests if the method launches an exception when the dining room of a color
     * is full and the player tries to add another student.
     */
    @Test
    void testAddStudentIntoDiningRoomWhenFull() {
        for (int i = 0; i < 10; i++) {
            try {
                schoolBoard.addStudentIntoDiningRoom(Color.GREEN);
            } catch (IllegalClientInputException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(IllegalClientInputException.class, () -> schoolBoard.addStudentIntoDiningRoom(Color.GREEN));
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
        try {
            schoolBoard.addStudentIntoDiningRoom(Color.GREEN);
        } catch (IllegalClientInputException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, schoolBoard.getStudentsNumber(Color.GREEN));
    }

    /**
     * Tests if the student is removed when there are not students of the color chosen by the player.
     */
    @Test
    void testRemoveWhenThereAreNoStudents() {
        Color student = Color.BLUE;
        assertThrows(IllegalArgumentException.class, () -> schoolBoard.removeStudentFromEntrance(student));
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
    void testCorrectInitialNumTowers() {
        assertEquals(8, schoolBoard.getNumTowers());
        assertEquals(6, threePlayersSchoolBoard.getNumTowers());
    }

    /**
     * Tests if returns true when the entrance of the school board is empty.
     */
    @Test
    void testEmptyEntrance() {
        assertTrue(schoolBoard.isEntranceEmpty());
    }

    /**
     * Tests if returns true when the dining room of the school board is empty.
     */
    @Test
    void testEmptyDiningRoom() {
        assertTrue(schoolBoard.isDiningRoomEmpty());
    }
}