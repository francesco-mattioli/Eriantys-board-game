package it.polimi.ingsw.triton.launcher.network.message.clientmessage;

import it.polimi.ingsw.triton.launcher.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.network.message.MessageType;

public class CharacterCardChoice extends ClientMessage {
    private final CharacterCard selectedCharacterCard;

    public CharacterCardChoice(String username, CharacterCard selectedCharacterCard) {
        super(MessageType.CHARACTER_CARD_CHOICE, username);
        this.selectedCharacterCard = selectedCharacterCard;
    }

    public CharacterCard getSelectedCharacterCard() {
        return selectedCharacterCard;
    }
}
