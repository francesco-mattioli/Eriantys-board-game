package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;

public class CharacterCard12Reply extends CharacterCardReply{
    private final Color student;

    public CharacterCard12Reply(String senderUsername, Color student) {
        super(senderUsername);
        this.student = student;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException{
        visitor.visitForModify(this);
    }

    public Color getStudent() {
        return student;
    }
}
