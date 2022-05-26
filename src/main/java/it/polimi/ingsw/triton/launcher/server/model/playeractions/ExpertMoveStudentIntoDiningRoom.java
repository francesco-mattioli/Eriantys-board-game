package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.GeneralCoinSupply;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EmptyGeneralCoinSupplyException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.ErrorTypeID;

public class ExpertMoveStudentIntoDiningRoom extends MoveStudentIntoDiningRoom {
    private final GeneralCoinSupply generalCoinSupply;

    /**
     * @param student           the color of student to move into the dining room.
     * @param currentPlayer     the current player who executes the action.
     * @param generalCoinSupply the coin supply of the game.
     */
    public ExpertMoveStudentIntoDiningRoom(Color student, Player currentPlayer, GeneralCoinSupply generalCoinSupply) {
        super(student, currentPlayer);
        this.generalCoinSupply = generalCoinSupply;
    }

    /**
     * School board contains a matrix, that specifies for each position if a coin is available
     *
     * @param studentColor is the color of student
     * @return true if the coin in that position was not taken anymore
     */
    private boolean isMultiple3(Color studentColor) {
        int numberOfThisColorStudents = currentPlayer.getSchoolBoard().getStudentsNumber(studentColor);
        boolean returnValue = false;
        if (numberOfThisColorStudents != 0) {
            returnValue = currentPlayer.getSchoolBoard().getAvailableCoins()[studentColor.ordinal()][numberOfThisColorStudents - 1];
            currentPlayer.getSchoolBoard().getAvailableCoins()[studentColor.ordinal()][numberOfThisColorStudents - 1] = false;
        }
        return returnValue;
    }

    /**
     * Calls increaseValue() to add a coin in the wallet and to remove a coin from the supply.
     */
    private void updateWallet() throws EmptyGeneralCoinSupplyException {
        generalCoinSupply.decrement();
        currentPlayer.getWallet().increaseValue();
    }

    /**
     * Adds a student in the schoolBoard dining room and check if there is
     * a number of students which is multiple of 3.
     */
    @Override
    public void execute() throws IllegalClientInputException, EmptyGeneralCoinSupplyException {
        if (currentPlayer.getSchoolBoard().isEntranceEmpty() || noStudentsColorInTheEntrance())
            throw new IllegalClientInputException(ErrorTypeID.NO_STUDENT_WITH_COLOR_ENTRANCE);
        else {
            currentPlayer.getSchoolBoard().removeStudentFromEntrance(student);
            currentPlayer.getSchoolBoard().addStudentIntoDiningRoom(student);
            if (isMultiple3(student))
                updateWallet();
        }
    }
}
