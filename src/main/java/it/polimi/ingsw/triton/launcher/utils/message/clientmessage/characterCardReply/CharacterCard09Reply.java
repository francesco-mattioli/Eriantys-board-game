package it.polimi.ingsw.triton.launcher.utils.message.clientmessage.characterCardReply;

import it.polimi.ingsw.triton.launcher.server.model.Game;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CardEffect09;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

public class CharacterCard09Reply extends CharacterCardReply{
    private final Color color;

    public CharacterCard09Reply(String senderUsername, Color color) {
        super(MessageType.CHARACTER_CARD_09_PARAMETER, senderUsername);
        this.color = color;
    }

    @Override
    public void modifyModel(Game game) throws IllegalClientInputException, EndGameException {
        game.applyCharacterCardEffect(9, new CardEffect09(game.getIslands(), color));
    }
}
