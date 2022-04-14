package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.model.playeractions.MoveStudentOntoIsland;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveStudentOntoIslandTest {
    private SchoolBoard schoolBoard;
    private Island dest;
    private Color student;

    @BeforeEach
    void setup(){
        schoolBoard = new SchoolBoard(TowerColor.BLACK);
        dest = new Island(1);
        student = Color.BLUE;
    }

    @AfterEach
    void tearDown(){
        schoolBoard = null;
        dest = null;
        student = null;
    }

    /**
     * Test if the entrance of the school board has not any students
     */
    @Test
    void testIfTheEntranceIsEmpty(){
        MoveStudentOntoIsland mi = new MoveStudentOntoIsland(schoolBoard, student, dest);
        assertThrows(RuntimeException.class, mi::execute);
    }

    /**
     * Test if the entrance of the school board has not any students
     */
    @Test
    void testIfThereIsNotStudentColor(){
        MoveStudentOntoIsland mi = new MoveStudentOntoIsland(schoolBoard, student, dest);
        schoolBoard.addStudentIntoEntrance(Color.RED);
        schoolBoard.addStudentIntoEntrance(Color.GREEN);
        schoolBoard.addStudentIntoEntrance(Color.YELLOW);
        assertThrows(RuntimeException.class, mi::execute);
    }

    /**
     * Test if the student is removed from entrance and added to the island
     */
    @Test
    void testStudentsAreMovedOntoIsland() {
        MoveStudentOntoIsland mi = new MoveStudentOntoIsland(schoolBoard, student, dest);
        schoolBoard.addStudentIntoEntrance(student);
        int numBlueStudentsEntrance = schoolBoard.getEntrance()[Color.BLUE.ordinal()];
        int numBlueStudentsIsland = dest.getStudents()[Color.BLUE.ordinal()];
        schoolBoard.addStudentIntoEntrance(Color.RED);
        mi.execute();
        assertEquals(numBlueStudentsEntrance-1, schoolBoard.getEntrance()[student.ordinal()]);
        assertEquals(numBlueStudentsIsland+1, dest.getStudents()[Color.BLUE.ordinal()]);
    }
}