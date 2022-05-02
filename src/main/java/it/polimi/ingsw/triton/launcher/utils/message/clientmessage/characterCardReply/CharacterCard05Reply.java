package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;

import java.util.NoSuchElementException;

/**
 * This message contains the parameter to apply the effect.
 */
public class CharacterCard05Reply  extends CharacterCardReply{
    private final int islandID;
    public CharacterCard05Reply(String senderUsername, int islandID) {
        super(senderUsername);
        this.islandID = islandID;
    }


    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException, NoSuchElementException {
        visitor.visitForModify(this);
    }

    public int getIslandID() {
        return islandID;
    }
}
