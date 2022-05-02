package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class MotherNatureReply extends ClientMessage{
    private final int numSteps;
    public MotherNatureReply(String senderUsername, int numSteps) {
        super(MessageType.NUMBER_STEPS_MOTHER_NATURE_REPLY, senderUsername);
        this.numSteps = numSteps;
    }

    public int getNumSteps() {
        return numSteps;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, EndGameException {
        game.moveMotherNature(numSteps);
    }

    @Override
    public void createStandardNextMessage(Game game, VirtualView virtualView) {
        virtualView.askCloudTile();
    }

    @Override
    public void createInputErrorMessage(Game game, VirtualView virtualView) {
        virtualView.askNumberStepsMotherNature();
    }
}
