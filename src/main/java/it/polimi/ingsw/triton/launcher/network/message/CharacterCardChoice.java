package it.polimi.ingsw.triton.launcher.network.message;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;

public class CharacterCardChoice extends Message{
    private CharacterCard selectedCharacterCard;
    public CharacterCardChoice(String nickname, CharacterCard selectedCharacterCard) {
        super(nickname, MessageType.CHARACTER_CARD_CHOICE);
        this.selectedCharacterCard = selectedCharacterCard;
    }
}
