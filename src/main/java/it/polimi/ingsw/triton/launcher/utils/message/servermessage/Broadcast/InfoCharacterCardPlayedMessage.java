package it.polimi.ingsw.triton.launcher.utils.message.servermessage.Broadcast;

import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.utils.message.MessageType;

/**
 * This message communicates to the players which character card was selected by the player.
 */
public class InfoCharacterCardPlayedMessage extends BroadcastServerMessage{
    private final String playerUsername;
    private final CharacterCard characterCard;
    public InfoCharacterCardPlayedMessage(String playerUsername, CharacterCard characterCard) {
        super(MessageType.INFO_CHARACTER_CARD_PLAYED);
        this.playerUsername = playerUsername;
        this.characterCard = characterCard;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public CharacterCard getCharacterCard() {
        return characterCard;
    }
}
