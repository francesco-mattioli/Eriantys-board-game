package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect10;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;
import it.polimi.ingsw.triton.launcher.utils.message.clientmessage.visitors.ClientMessageModifierVisitor;

import java.util.NoSuchElementException;

public class CharacterCard10Reply extends CharacterCardReply{
    private final int[] fromEntrance;
    private final int[] fromDiningRoom;

    public CharacterCard10Reply(String senderUsername, int[] fromEntrance, int[] fromDiningRoom) {
        super(senderUsername);
        this.fromDiningRoom = fromDiningRoom;
        this.fromEntrance = fromEntrance;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException{
        visitor.visitForModify(this);
    }

    public int[] getFromEntrance() {
        return fromEntrance;
    }

    public int[] getFromDiningRoom() {
        return fromDiningRoom;
    }
}
