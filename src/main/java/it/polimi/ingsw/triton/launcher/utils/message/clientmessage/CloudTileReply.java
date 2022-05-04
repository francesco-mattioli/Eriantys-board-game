package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.utils.exceptions.ChangeTurnException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;

import java.util.NoSuchElementException;

public class CloudTileReply extends ClientMessage {
    private final int selectedCloudTileID;

    public CloudTileReply(String username, int selectedCloudTileID) {
        super(username);
        this.selectedCloudTileID = selectedCloudTileID;
    }

    public int getSelectedCloudTileID() {
        return selectedCloudTileID;
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
    public void createInputErrorMessage(ClientMessageErrorVisitor visitor) {
        visitor.visitForSendErrorMessage(this);
    }

    @Override
    public void createExceptionalNextMessage(ClientMessageExceptionalVisitor visitor) {
        visitor.visitForSendExceptionalMessage(this);
    }
}
