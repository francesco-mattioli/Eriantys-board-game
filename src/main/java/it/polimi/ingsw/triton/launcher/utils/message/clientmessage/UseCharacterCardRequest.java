package it.polimi.ingsw.triton.launcher.utils.message.clientmessage;

import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageErrorVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageExceptionalVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageStandardVisitor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

/**
 * This message is sent by the client to communicate to server that the current player wants to
 * play a character card.
 * The parameter characterCardID is the id of the chosen character card.
 */
public class UseCharacterCardRequest extends ClientMessage {
    private final int characterCardID;

    public UseCharacterCardRequest(int characterCardID) {
        this.characterCardID = characterCardID;
    }

    public int getCharacterCardID() {
        return characterCardID;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, CharacterCardWithParametersException, EndGameException {
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
