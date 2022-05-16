package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.cardeffects.CharacterCard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This message is sent by the server to communicate to the players some game information.
 * It's used when the game mode is expert.
 */
public class ExpertGameInfoMessage extends GameInfoMessage{
    private final ArrayList<CharacterCard> availableCharacterCards;

    public ExpertGameInfoMessage(ArrayList<CharacterCard> availableCharacterCards, List<Island> islands, Island motherNaturePosition, Map<String, SchoolBoard> schoolBoards, ArrayList<CloudTile> cloudTiles, String[] professors, Map<String,Wizard> chosenWizardsPerUsername) {
        super(islands, motherNaturePosition, schoolBoards, cloudTiles, professors,chosenWizardsPerUsername);
        super.expertMode=true;
        this.availableCharacterCards = availableCharacterCards;
    }

    public ArrayList<CharacterCard> getAvailableCharacterCards() {
        return availableCharacterCards;
    }

    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
