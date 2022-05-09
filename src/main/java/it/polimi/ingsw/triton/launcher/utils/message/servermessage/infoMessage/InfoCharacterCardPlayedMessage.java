package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;


import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

import java.util.ArrayList;
import java.util.Map;

/**
 * This message communicates to the players which character card was selected by the player.
 */
public class InfoCharacterCardPlayedMessage extends InfoMessage {
    private final String playerUsername;
    private final CharacterCard characterCard;
    private final ArrayList<Island> updatedIslands;
    private final Map<String, SchoolBoard> updatedSchoolBoards;
    private final String choiceDescription;
    public InfoCharacterCardPlayedMessage(String playerUsername, CharacterCard characterCard, ArrayList<Island> updatedIslands, Map<String, SchoolBoard> updatedSchoolBoards) {
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

    public ArrayList<Island> getUpdatedIslands() {
        return updatedIslands;
    }

    public Map<String, SchoolBoard> getUpdatedSchoolBoards() {
        return updatedSchoolBoards;
    }
}
