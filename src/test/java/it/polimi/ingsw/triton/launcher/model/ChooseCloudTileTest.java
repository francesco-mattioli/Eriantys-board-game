package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.playeractions.ChooseCloudTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChooseCloudTileTest {

    /**
     * Test if the students move from the chosen cloud tile to the entrance of the school board
     */
    @Test
    void execute() {
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        CloudTile ct = new CloudTile(1);
        ChooseCloudTile cct = new ChooseCloudTile(ct, schoolBoard);
        assertNotNull(cct);
        assertEquals(0, ct.getStudents()[Color.BLUE.ordinal()]);
        assertEquals(0, ct.getStudents()[Color.RED.ordinal()]);
        assertEquals(0, ct.getStudents()[Color.GREEN.ordinal()]);
        ct.setStudents(Color.BLUE, Color.RED, Color.GREEN);
        assertEquals(1, ct.getStudents()[Color.BLUE.ordinal()]);
        assertEquals(1, ct.getStudents()[Color.RED.ordinal()]);
        assertEquals(1, ct.getStudents()[Color.GREEN.ordinal()]);
        cct.execute();
        assertEquals(0, ct.getStudents()[Color.BLUE.ordinal()]);
        assertEquals(0, ct.getStudents()[Color.RED.ordinal()]);
        assertEquals(0, ct.getStudents()[Color.GREEN.ordinal()]);
    }
}