package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies;

import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

/**
 * This message is sent by the client to communicate to server the color chosen by the current player
 * in order to build and apply the effect of the character card 12.
 */
public class CharacterCard12Reply extends CharacterCardReply {
    private final Color student;

    public CharacterCard12Reply(Color student) {
        this.student = student;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException {
        visitor.visitForModify(this);
    }

    public Color getStudent() {
        return student;
    }
}
