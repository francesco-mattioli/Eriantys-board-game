package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.ChangeTurnException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

/**
 * This message is sent by the client to communicate to server the number of steps that mother nature has to do
 * according to the current player.
 */
public class MotherNatureReply extends ClientMessage {
    private final int numSteps;

    public MotherNatureReply(int numSteps) {
        this.numSteps = numSteps;
    }

    public int getNumSteps() {
        return numSteps;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException, ChangeTurnException {
        visitor.visitForModify(this);
    }

    @Override
    public void createStandardNextMessage(ClientMessageStandardVisitor visitor) {
        visitor.visitForSendStandardMessage(this);
    }

    @Override
    public void createExceptionalNextMessage(ClientMessageExceptionalVisitor visitor) {
        visitor.visitForSendExceptionalMessage(this);
    }

    @Override
    public void createInputErrorMessage(ClientMessageErrorVisitor visitor) {
        visitor.visitForSendErrorMessage(this);
    }
}
