package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect03;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect05;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.NoSuchElementException;

/**
 * This message contains the parameter to apply the effect.
 */
public class CharacterCard05Reply  extends CharacterCardReply{
    private final int islandID;
    public CharacterCard05Reply(String senderUsername, int islandID) {
        super(MessageType.CHARACTER_CARD_05_PARAMETER, senderUsername);
        this.islandID = islandID;
    }


    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, EndGameException, NoSuchElementException {
        game.applyCharacterCardEffect(5, new CardEffect05(game.getIslandByID(islandID), game.getCharacterCardByID(5)));
    }
}
