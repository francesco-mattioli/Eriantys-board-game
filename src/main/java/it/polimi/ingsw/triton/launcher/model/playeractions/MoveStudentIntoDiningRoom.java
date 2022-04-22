package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.model.player.Wallet;

public class MoveStudentIntoDiningRoom implements Action {
    private final Color student;
    private final Wallet wallet;
    private final SchoolBoard schoolBoard;

    /**
     * @param student     the color of student to move into the dining room.
     * @param wallet      contains the number of coins of a player.
     * @param schoolBoard player's school board where to add the student.
     */
    public MoveStudentIntoDiningRoom(Color student, Wallet wallet, SchoolBoard schoolBoard) {
        this.student = student;
        this.wallet = wallet;
        this.schoolBoard = schoolBoard;
    }

    /**
     * @param studentColor is the color of student
     * @return true if the number of students of a certain color is a multiple of 3, false otherwise.
     */
    private boolean isMultiple3(Color studentColor) {
        return (schoolBoard.getStudentsNumber(studentColor) % 3) == 0;
    }

    /**
     * Calls increaseValue() to add a coin in the wallet
     */
    private synchronized void updateWallet() {
        wallet.increaseValue();
    }

    /**
     * Adds a student in the schoolBoard dining room and check if there is
     * a number of students which is multiple of 3.
     */
    @Override
    public void execute() throws NullPointerException {
        if(student == null){
            throw new NullPointerException("Not valid color");
        }else{
            schoolBoard.addStudentIntoDiningRoom(student);
            if (isMultiple3(student))
                updateWallet();
        }
    }
}
