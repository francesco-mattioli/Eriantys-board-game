package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.model.enums.TowerColor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;

public class TowerColorReply extends ClientMessage {
    private final TowerColor playerColor;

    public TowerColorReply(String username, TowerColor playerColor) {
        super(username);
        this.playerColor = playerColor;
    }
    public TowerColor getPlayerColor() {
        return playerColor;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException {
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

    //normal execution: send same message to next player


}
