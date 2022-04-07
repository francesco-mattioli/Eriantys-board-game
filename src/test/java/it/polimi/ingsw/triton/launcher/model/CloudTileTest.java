package it.polimi.ingsw.triton.launcher.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTileTest {

    @Test
    void setStudents() {
    }

    @Test
    void testSetStudents() {
    }

    @Test
    void addStudent() {
    }

    @Test
    void getStudents() {
    }

    /**
     * Test if the student is removed from the cloud tile
     */
    @Test
    void removeStudentFromCloudTile() {
        CloudTile ct = new CloudTile(1);
        Color student = Color.YELLOW;
        assertNotNull(ct);
        ct.setStudents(Color.BLUE, Color.RED, Color.GREEN);
        Exception exception = Assertions.assertThrows(RuntimeException.class, ()->ct.removeStudentFromCloudTile(student));
        assertEquals("There aren't " + student.name() + " students on this cloud tile", exception.getMessage());
        Color student2 = Color.BLUE;
        assertEquals(1, ct.getStudents()[student2.ordinal()]);
        ct.removeStudentFromCloudTile(student2);
        assertEquals(0, ct.getStudents()[student2.ordinal()]);
    }
}