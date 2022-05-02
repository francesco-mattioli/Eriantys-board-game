package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect01;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

/**
 * This message contains the parameters to apply the effect.
 */
public class CharacterCard01Reply extends CharacterCardReply{
    private final Color student;
    private final int islandID;
    public CharacterCard01Reply(String senderUsername, Color student, int islandID) {
        super(MessageType.CHARACTER_CARD_01_PARAMETER, senderUsername);
        this.islandID = islandID;
        this.student = student;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, EndGameException, NoSuchElementException {
        game.applyCharacterCardEffect(1, new CardEffect01(game.getCharacterCardByID(1), student, game.getIslandByID(islandID), game.getBag()));
    }
}
