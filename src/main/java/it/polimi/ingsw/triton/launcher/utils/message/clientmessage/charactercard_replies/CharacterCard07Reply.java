package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.charactercard_replies;

import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

/**
 * This message is sent by the client to communicate to server the students from current player's entrance and from the card
 * chosen by the current player in order to build and apply the effect of the character card 07.
 */
public class CharacterCard07Reply extends CharacterCardReply {
    private final int[] fromCard;
    private final int[] fromSchoolBoard;
    private final int[] studentsOnCard;

    public CharacterCard07Reply(int[] fromCard, int[] fromSchoolBoard, int[] studentsOnCard) {
        this.fromCard = fromCard;
        this.fromSchoolBoard = fromSchoolBoard;
        this.studentsOnCard = studentsOnCard;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException {
        visitor.visitForModify(this);
    }

    public int[] getFromCard() {
        return fromCard;
    }

    public int[] getFromSchoolBoard() {
        return fromSchoolBoard;
    }

    public int[] getStudentsOnCard() {
        return studentsOnCard;
    }
}
