package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect03;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

/**
 * This message contains the parameter to apply the effect.
 */
public class CharacterCard03Reply  extends CharacterCardReply{
    private final int islandID;
    public CharacterCard03Reply(String senderUsername, int islandID) {
        super(MessageType.CHARACTER_CARD_03_PARAMETER, senderUsername);
        this.islandID = islandID;
    }


    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, EndGameException, NoSuchElementException {
        game.applyCharacterCardEffect(3, new CardEffect03(game.getIslandByID(islandID), game.getPlayers(), game.getProfessors()));
    }
}
