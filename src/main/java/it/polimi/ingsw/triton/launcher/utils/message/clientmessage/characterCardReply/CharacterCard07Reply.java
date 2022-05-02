package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.server.controller.visitors.ClientMessageModifierVisitor;

public class CharacterCard07Reply  extends CharacterCardReply{
    private final int[] fromCard;
    private final int[] fromSchoolBoard;
    private final int[] studentsOnCard;

    public CharacterCard07Reply(String senderUsername, int[] fromCard, int[] fromSchoolBoard, int[] studentsOnCard) {
        super(senderUsername);
        this.fromCard = fromCard;
        this.fromSchoolBoard = fromSchoolBoard;
        this.studentsOnCard = studentsOnCard;
    }

    @Override
    public void modifyModel(ClientMessageModifierVisitor visitor) throws IllegalClientInputException, EndGameException{
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
