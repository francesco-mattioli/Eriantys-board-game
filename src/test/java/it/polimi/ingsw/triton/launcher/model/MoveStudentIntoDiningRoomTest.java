package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.model.player.Wallet;
import it.polimi.ingsw.triton.launcher.model.playeractions.MoveStudentIntoDiningRoom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveStudentIntoDiningRoomTest {

    /**
     * Test if number of students is multiple of 3
     */
    @Test
    void testIsMultiple3() {
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        Wallet wallet = new Wallet();
        Color student = Color.BLUE;
        schoolBoard.addStudentIntoDiningRoom(student);
        MoveStudentIntoDiningRoom mv = new MoveStudentIntoDiningRoom(student, wallet, schoolBoard);
        assertFalse(mv.isMultiple3(student));
        schoolBoard.addStudentIntoDiningRoom(student);
        assertFalse(mv.isMultiple3(student));
        schoolBoard.addStudentIntoDiningRoom(student);
        assertTrue(mv.isMultiple3(student));
    }

    /**
     * Test if the wallet is increased after updateWallet() method
     */
    @Test
    void updateWallet() {
        Wallet wallet = new Wallet();
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        MoveStudentIntoDiningRoom mv = new MoveStudentIntoDiningRoom(Color.BLUE, wallet, schoolBoard);
        mv.updateWallet();
        assertEquals(1, wallet.getValue());
        for(int i = 0; i < 3; i++) {
            schoolBoard.addStudentIntoDiningRoom(Color.BLUE);
            mv.updateWallet();
        }
        assertEquals(4, wallet.getValue());
    }

    /**
     * Test if the student is added in the dining room and check if the number of students
     * in the dining room is a multiple of 3
     */
    @Test
    void execute() {
        Wallet wallet = new Wallet();
        SchoolBoard schoolBoard = new SchoolBoard(TowerColor.BLACK);
        MoveStudentIntoDiningRoom mv = new MoveStudentIntoDiningRoom(Color.BLUE, wallet, schoolBoard);
        mv.execute();
        assertEquals(1, schoolBoard.getStudentsNumber(Color.BLUE));
        for(int i = 0; i < 3; i++){
            mv.execute();
        }
        assertEquals(1, wallet.getValue());
    }
}