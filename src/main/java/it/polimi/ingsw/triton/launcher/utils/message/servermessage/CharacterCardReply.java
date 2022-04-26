package it.polimi.ingsw.triton.launcher.utils.message.servermessage;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

import java.util.ArrayList;

public class CharacterCardReply extends ServerMessage {
    private final ArrayList<CharacterCard> availableCharacterCards;
    private String receiverUsername;
    public CharacterCardReply(ArrayList<CharacterCard> availableCharacterCards, String receiverUsername) {
        super(MessageType.CHARACTER_CARD_REPLY, receiverUsername);
        this.availableCharacterCards = availableCharacterCards;
    }

    public ArrayList<CharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }
}
