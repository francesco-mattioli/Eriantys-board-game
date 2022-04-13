package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.model.playeractions.MoveStudentOntoIsland;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveStudentOntoIslandTest {

    /**
     * Test if the student is removed from entrance and added to the island
     */
    @Test
    void execute() {
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        Color student = Color.BLUE;
        Island dest = new Island(1);
        MoveStudentOntoIsland mi = new MoveStudentOntoIsland(schoolBoard, student, dest);
        Exception exception = Assertions.assertThrows(RuntimeException.class, mi::execute);
        assertEquals("No students of color " + student.name() + " at entrance", exception.getMessage());
        schoolBoard.addStudentIntoEntrance(student);
        mi.execute();
        assertEquals(0, schoolBoard.getEntrance()[student.ordinal()]);
    }
}