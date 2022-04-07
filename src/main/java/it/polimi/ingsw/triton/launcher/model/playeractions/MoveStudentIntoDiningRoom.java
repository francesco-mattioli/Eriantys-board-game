package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.SchoolBoard;
import it.polimi.ingsw.triton.launcher.model.Wallet;

/**
 * Represents the action of moving a student from the entrance to the dining room of the scholl board.
 */
public class MoveStudentIntoDiningRoom implements Action {
    private Color student;
    private Wallet wallet;
    private SchoolBoard schoolBoard;

    /**
     * @param student the color of student to move into the dining room.
     * @param wallet contains the number of coins of a player.
     * @param schoolBoard player's schoolboard where to add the student.
     */
    public MoveStudentIntoDiningRoom(Color student, Wallet wallet, SchoolBoard schoolBoard){
        this.student = student;
        this.wallet = wallet;
        this.schoolBoard = schoolBoard;
    }

    /**
     * @param student the color of student
     * @return true if the number of students of a certain color is a multiple of 3, false otherwise.
     */
    public boolean isMultiple3(Color student){
        return (schoolBoard.getStudentsNumber(student) % 3) == 0;
    }

    /**
     * Calls increaseValue() to add a coin in the wallet
     */
    public void updateWallet(){
        wallet.increaseValue();
    }

    /**
     * Adds a student in the schoolBoard dining room and check if there is
     * a number of students which is multiple of 3.
     */
    @Override
    public void execute() {
        schoolBoard.addStudentIntoDiningRoom(student);
        if(isMultiple3(student))
            updateWallet();
    }
}
