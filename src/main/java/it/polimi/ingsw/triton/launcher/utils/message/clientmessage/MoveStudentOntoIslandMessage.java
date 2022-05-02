package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class MoveStudentOntoIslandMessage extends ClientMessage{
    private final int islandID;
    private final Color student;

    public int getIslandID() {
        return islandID;
    }

    public Color getStudent() {
        return student;
    }

    public MoveStudentOntoIslandMessage(String senderUsername, int islandID, Color student) {
        super(MessageType.MOVE_STUDENT_ONTO_ISLAND, senderUsername);
        this.islandID = islandID;
        this.student = student;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, LastMoveException {
        game.executeActionMoveStudentToIsland(student, islandID);
    }

    @Override
    public void createStandardNextMessage(Game game, VirtualView virtualView) {
        virtualView.askMoveStudentFromEntrance();
    }

    @Override
    public void createExceptionalNextMessage(Game game, VirtualView virtualView) {
        virtualView.askNumberStepsMotherNature();
    }

    @Override
    public void createInputErrorMessage(Game game, VirtualView virtualView) {
        virtualView.askMoveStudentFromEntrance();
    }
}
