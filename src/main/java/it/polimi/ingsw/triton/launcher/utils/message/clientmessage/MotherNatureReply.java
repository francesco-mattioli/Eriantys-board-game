package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.view.VirtualView;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.visitors.ClientMessageStandardVisitor;

import java.util.NoSuchElementException;

public class MotherNatureReply extends ClientMessage{
    private final int numSteps;
    public MotherNatureReply(String senderUsername, int numSteps) {
        super(senderUsername);
        this.numSteps = numSteps;
    }

    public int getNumSteps() {
        return numSteps;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException {
        visitor.visitForModify(this);
    }

    @Override
    public void createStandardNextMessage(ClientMessageStandardVisitor visitor) {
        visitor.visitForSendStandardMessage(this);
    }

    @Override
    public void createInputErrorMessage(ClientMessageErrorVisitor visitor) {
        visitor.visitForSendErrorMessage(this);
    }
}
