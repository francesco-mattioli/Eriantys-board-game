package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.EmptyGeneralCoinSupplyMessage;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage.infoMessageWithReceiver.UpdateWalletMessage;
import it.polimi.ingsw.triton.launcher.utils.obs.Observable;

import java.util.NoSuchElementException;

public class MoveStudentIntoDiningRoom extends Observable<InfoMessage> implements Action {
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



    protected boolean noStudentsColorInTheEntrance(){
        return currentPlayer.getSchoolBoard().getEntrance()[student.ordinal()] == 0;
    }



    /**
     * Adds a student in the schoolBoard dining room and check if there is
     * a number of students which is multiple of 3.
     */
    @Override
    public void execute() throws IllegalClientInputException {
        if(currentPlayer.getSchoolBoard().isEntranceEmpty())
            throw new IllegalClientInputException();
        else if(noStudentsColorInTheEntrance())
            throw new IllegalClientInputException();
        else{
            currentPlayer.getSchoolBoard().removeStudentFromEntrance(student);
            currentPlayer.getSchoolBoard().addStudentIntoDiningRoom(student);
        }
    }
}
