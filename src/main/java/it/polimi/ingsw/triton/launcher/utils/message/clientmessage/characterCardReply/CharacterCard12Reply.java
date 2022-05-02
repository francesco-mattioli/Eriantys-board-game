package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect12;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.CharacterCardWithParametersException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.LastMoveException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

public class CharacterCard12Reply extends CharacterCardReply{
    private final Color student;

    public CharacterCard12Reply(String senderUsername, Color student) {
        super(MessageType.CHARACTER_CARD_12_PARAMETER, senderUsername);
        this.student = student;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, EndGameException{
        game.applyCharacterCardEffect(12, new CardEffect12(student, game.getPlayers(), game.getBag()));
    }
}
