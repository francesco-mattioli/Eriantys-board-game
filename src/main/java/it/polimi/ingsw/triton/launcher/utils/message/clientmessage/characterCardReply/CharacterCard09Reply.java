package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;

public class CharacterCard09Reply extends CharacterCardReply{
    private final Color color;

    public CharacterCard09Reply(String senderUsername, Color color) {
        super(senderUsername);
        this.color = color;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException {
        visitor.visitForModify(this);
    }

    public Color getColor() {
        return color;
    }
}
