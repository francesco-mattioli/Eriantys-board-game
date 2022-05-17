package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;
import java.util.List;
import java.util.Map;

/**
 * This message is sent by the server to communicate which character card is selected by the current player.
 * The parameter choiceDescription is used on client-side to tell to other players the id of the chosen
 * character card.
 */
public class InfoCharacterCardPlayedMessage extends InfoMessage {
    private final String playerUsername;
    private final CharacterCard characterCard;
    private final List<Island> updatedIslands;
    private final Map<String, SchoolBoard> updatedSchoolBoards;
    private final String choiceDescription;
    public InfoCharacterCardPlayedMessage(String playerUsername, CharacterCard characterCard, List<Island> updatedIslands, Map<String, SchoolBoard> updatedSchoolBoards) {
        this.playerUsername = playerUsername;
        this.characterCard = characterCard;
        this.choiceDescription = playerUsername + " has just played the character card " + characterCard.getId();
        this.updatedIslands = updatedIslands;
        this.updatedSchoolBoards = updatedSchoolBoards;
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

    public List<Island> getUpdatedIslands() {
        return updatedIslands;
    }

    public Map<String, SchoolBoard> getUpdatedSchoolBoards() {
        return updatedSchoolBoards;
    }
}
