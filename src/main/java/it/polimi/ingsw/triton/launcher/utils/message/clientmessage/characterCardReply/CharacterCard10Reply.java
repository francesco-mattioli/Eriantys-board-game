package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;

/**
 * This message is sent by the client to communicate to server the students from current player's dining room
 * and current player's entrance chosen by him in order to build and apply the effect of the character card 10.
 */
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
