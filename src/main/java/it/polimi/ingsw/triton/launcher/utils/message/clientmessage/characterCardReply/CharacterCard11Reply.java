package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect11;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class CharacterCard11Reply extends CharacterCardReply{
    private final Color student;

    public CharacterCard11Reply(String senderUsername, Color student) {
        super(MessageType.CHARACTER_CARD_11_PARAMETER, senderUsername);
        this.student = student;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, EndGameException{
        game.applyCharacterCardEffect(11, new CardEffect11(student, game.getPlayerByUsername(senderUsername).getSchoolBoard(), game.getBag(), game.getCharacterCardByID(11)));
    }
}
