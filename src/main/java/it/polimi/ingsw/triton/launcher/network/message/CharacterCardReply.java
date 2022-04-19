package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.Game;
import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;

import java.util.ArrayList;

public class CharacterCardReply extends Message{
    private ArrayList<CharacterCard> availableCharacterCards;
    public CharacterCardReply(ArrayList<CharacterCard> availableCharacterCards) {
        super(Game.NAME_SERVER, MessageType.CHARACTER_CARD_REPLY);
        this.availableCharacterCards = availableCharacterCards;
    }
}
