package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

public class MoveStudentIntoDiningRoom implements Action {
    protected final Color student;
    protected final Player currentPlayer;


    /**
     * @param student     the color of student to move into the dining room.
     * @param currentPlayer the current player who executes the action.
     */
    public MoveStudentIntoDiningRoom(Color student, Player currentPlayer) {
        this.student = student;
        this.currentPlayer = currentPlayer;

    }


    /**
     * @return true if the player has not any students of the specified color in his entrance, false otherwise.
     */
    protected boolean noStudentsColorInTheEntrance(){
        return currentPlayer.getSchoolBoard().getEntrance()[student.ordinal()] == 0;
    }



    /**
     * Adds a student in the schoolBoard dining room and check if there is
     * a number of students which is multiple of 3.
     */
    @Override
    public void execute() throws IllegalClientInputException, EmptyGeneralCoinSupplyException {
        if(currentPlayer.getSchoolBoard().isEntranceEmpty() || noStudentsColorInTheEntrance())
            throw new IllegalClientInputException(ErrorTypeID.NO_STUDENT_WITH_COLOR_ENTRANCE);
        else{
            currentPlayer.getSchoolBoard().removeStudentFromEntrance(student);
            currentPlayer.getSchoolBoard().addStudentIntoDiningRoom(student);
        }
    }
}
