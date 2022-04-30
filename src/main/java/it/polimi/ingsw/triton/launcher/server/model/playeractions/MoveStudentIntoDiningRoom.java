package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.server.model.player.Wallet;
import it.polimi.ingsw.triton.launcher.utils.IllegalClientInputException;

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

    private boolean isEmptyEntrance(){
        int numStudentsEntrance = 0;
        for(int i = 0; i < schoolBoard.getEntrance().length; i++){
            numStudentsEntrance += schoolBoard.getEntrance()[i];
        }
        return numStudentsEntrance == 0;
    }

    private boolean noStudentsColorInTheEntrance(){
        return schoolBoard.getEntrance()[student.ordinal()] == 0;
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
    public void execute() throws NullPointerException, IllegalClientInputException {
        if(isEmptyEntrance())
            throw new IllegalClientInputException();
        else if(noStudentsColorInTheEntrance())
            throw new IllegalClientInputException();
        else{
            schoolBoard.addStudentIntoDiningRoom(student);
            if (isMultiple3(student))
                updateWallet();
        }
    }
}
