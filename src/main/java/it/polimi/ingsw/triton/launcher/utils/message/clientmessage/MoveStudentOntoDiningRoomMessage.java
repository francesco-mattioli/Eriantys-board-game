package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class MoveStudentOntoDiningRoomMessage extends ClientMessage {
    private final Color student;

    public Color getStudent() {
        return student;
    }

    public MoveStudentOntoDiningRoomMessage(String senderUsername, Color student) {
        super(MessageType.MOVE_STUDENT_ONTO_DININGROOM, senderUsername);
        this.student = student;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, LastMoveException {
        game.executeActionMoveStudentToDiningRoom(student);
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
