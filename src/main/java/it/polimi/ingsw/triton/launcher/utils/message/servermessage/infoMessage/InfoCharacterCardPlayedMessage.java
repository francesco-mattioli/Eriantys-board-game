package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

/**
 * This message communicates to the players which character card was selected by the player.
 */
public class InfoCharacterCardPlayedMessage extends InfoMessage {
    private final String playerUsername;
    private final CharacterCard characterCard;
    private final String choiceDescription;
    public InfoCharacterCardPlayedMessage(String playerUsername, CharacterCard characterCard) {
        this.playerUsername = playerUsername;
        this.characterCard = characterCard;
        this.choiceDescription = playerUsername + " has just played the character card " + characterCard.getId();
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public CharacterCard getCharacterCard() {
        return characterCard;
    }

    @Override
    public void accept(ServerMessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public String getChoiceDescription() {
        return choiceDescription;
    }
}
