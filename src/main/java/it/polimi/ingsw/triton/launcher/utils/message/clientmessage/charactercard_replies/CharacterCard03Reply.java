package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies;

import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

import java.util.NoSuchElementException;

/**
 * This message is sent by the client to communicate to server the id of the island chosen by the current player
 * in order to build and apply the effect of the character card 03.
 */
public class CharacterCard03Reply extends CharacterCardReply {
    private final int islandID;

    public CharacterCard03Reply(int islandID) {
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
