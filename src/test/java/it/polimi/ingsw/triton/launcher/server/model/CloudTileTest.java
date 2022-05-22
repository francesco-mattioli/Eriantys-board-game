package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTileTest {

    private CloudTile cloudTile;

    @BeforeEach
    public void setUp(){
        cloudTile = new CloudTile(1);
    }

    @AfterEach
    public void tearDown(){
        cloudTile = null;
    }

    /**
     * Tests if the student is added on the cloud tile.
     */
    @Test
    void addOneStudentWhenCloudTileHasZero() {
        int current = 0;
        cloudTile.setStudents(Color.BLUE);
        for (int i = 0; i < cloudTile.getStudents().length; i++){
            current += cloudTile.getStudents()[i];
        }
        assertEquals(1,current);
    }


    /**
     * Tests if the method throws an exception when the color is null.
     */
    @Test
    void setNullColor(){
        assertThrows(IllegalArgumentException.class, () -> cloudTile.setStudents(null));
    }

    /**
     * Tests if removeStudentFromCloudTile throws an exception when the cloud tile is empty.
     */
    @Test
    void throwExceptionWhenCloudTileIsEmpty() {
        assertThrows(IllegalArgumentException.class, ()->cloudTile.removeStudentFromCloudTile(Color.YELLOW));
    }

    /**
     * Tests if removeStudentFromCloudTile removes the correct student.
     */
    @Test
    void removeOneBlueStudentWhenThereIsOne(){
        cloudTile.setStudents(Color.BLUE);
        cloudTile.setStudents(Color.RED);
        cloudTile.setStudents(Color.GREEN);
        cloudTile.removeStudentFromCloudTile(Color.BLUE);
        assertEquals(0, cloudTile.getStudents()[Color.BLUE.ordinal()]);
    }
}