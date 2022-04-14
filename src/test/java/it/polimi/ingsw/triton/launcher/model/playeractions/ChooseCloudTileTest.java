package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.CloudTile;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.model.playeractions.ChooseCloudTile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChooseCloudTileTest {
    private CloudTile ct;
    private SchoolBoard schoolBoard;

    @BeforeEach
    void setup(){
        ct = new CloudTile(1);
        schoolBoard = new SchoolBoard(TowerColor.BLACK);
    }

    @AfterEach
    void tearDown(){
        ct = null;
        schoolBoard = null;
    }

    /**
     * Test if the initial number of students of cloud tile is 0.
     */
    @Test
    void testInitialNumberStudentCloudTile() {
        ChooseCloudTile cct = new ChooseCloudTile(ct, schoolBoard);
        assertEquals(0, ct.getStudents()[Color.BLUE.ordinal()]);
        assertEquals(0, ct.getStudents()[Color.RED.ordinal()]);
        assertEquals(0, ct.getStudents()[Color.GREEN.ordinal()]);
    }

    /**
     * Test if the cloud tile is already chosen.
     */
    @Test
    void testIfCloudTileIsAlreadyChosen() {
        ChooseCloudTile cct = new ChooseCloudTile(ct, schoolBoard);
        ct.setStudents(Color.BLUE, Color.RED, Color.GREEN);
        cct.execute();
        ChooseCloudTile cct2 = new ChooseCloudTile(ct, schoolBoard);
        assertThrows(RuntimeException.class, cct2::execute);
    }

    /**
     * Test if the students are removed from the cloud tile.
     */
    @Test
    void testIfStudentsAreRemovedFromCloudTile(){
        ChooseCloudTile cct = new ChooseCloudTile(ct, schoolBoard);
        ct.setStudents(Color.BLUE, Color.RED, Color.GREEN);
        cct.execute();
        assertEquals(0, ct.getStudents()[Color.BLUE.ordinal()]);
        assertEquals(0, ct.getStudents()[Color.RED.ordinal()]);
        assertEquals(0, ct.getStudents()[Color.GREEN.ordinal()]);
    }

    /**
     * Test if the students are moved to the entrance of the school board.
     */
    @Test
    void testIfStudentsAreMovedToEntrance(){
        ChooseCloudTile cct = new ChooseCloudTile(ct, schoolBoard);
        ct.setStudents(Color.BLUE, Color.RED, Color.GREEN);
        cct.execute();
        assertEquals(1, schoolBoard.getEntrance()[Color.BLUE.ordinal()]);
        assertEquals(1, schoolBoard.getEntrance()[Color.RED.ordinal()]);
        assertEquals(1, schoolBoard.getEntrance()[Color.GREEN.ordinal()]);
    }
}