package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect07;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class CharacterCard07Reply  extends CharacterCardReply{
    private final int[] fromCard;
    private final int[] fromSchoolBoard;
    private final int[] studentsOnCard;

    public CharacterCard07Reply(String senderUsername, int[] fromCard, int[] fromSchoolBoard, int[] studentsOnCard) {
        super(MessageType.CHARACTER_CARD_07_PARAMETER, senderUsername);
        this.fromCard = fromCard;
        this.fromSchoolBoard = fromSchoolBoard;
        this.studentsOnCard = studentsOnCard;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, EndGameException{
        game.applyCharacterCardEffect(7, new CardEffect07(studentsOnCard, fromCard, fromSchoolBoard, game.getPlayerByUsername(senderUsername).getSchoolBoard()));
    }
}
