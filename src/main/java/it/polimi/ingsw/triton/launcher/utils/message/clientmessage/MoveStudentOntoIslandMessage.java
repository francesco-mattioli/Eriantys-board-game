package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;

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
        super(senderUsername);
        this.islandID = islandID;
        this.student = student;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws LastMoveException, IllegalClientInputException {
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

    @Override
    public void createExceptionalNextMessage(ClientMessageExceptionalVisitor visitor) {

        visitor.visitForSendExceptionalMessage(this);
    }
}
