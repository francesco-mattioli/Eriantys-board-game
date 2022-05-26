package it.polimi.ingsw.triton.launcher.utils.message.servermessage.infoMessage;

import it.polimi.ingsw.triton.launcher.client.ServerMessageVisitor;
import it.polimi.ingsw.triton.launcher.server.model.CloudTile;
import it.polimi.ingsw.triton.launcher.server.model.enums.Wizard;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.message.servermessage.InfoMessage;

import java.util.List;
import java.util.Map;

/**
 * This message is sent by the server to communicate to the players some game information.
 * It's used when the game mode is not expert, otherwise it's extended by ExpertGameInfoMessage.
 */
public class GameInfoMessage extends InfoMessage {
    private final List<Island> islands;
    private final Island motherNaturePosition;
    private final Map<String, SchoolBoard> schoolBoards;
    private final List<CloudTile> cloudTiles;
    private final String[] professors;
    protected boolean expertMode = false;
    private final Map<String, Wizard> chosenWizardsPerUsername;

    public GameInfoMessage(List<Island> islands, Island motherNaturePosition, Map<String, SchoolBoard> schoolBoards, List<CloudTile> cloudTiles, String[] professors, Map<String, Wizard> chosenWizardsPerUsername) {
        this.islands = islands;
        this.motherNaturePosition = motherNaturePosition;
        this.schoolBoards = schoolBoards;
        this.cloudTiles = cloudTiles;
        this.professors = professors;
        this.chosenWizardsPerUsername = chosenWizardsPerUsername;
    }

    public List<Island> getIslands() {
        return islands;
    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public Map<String, SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }

    public List<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    public String[] getProfessors() {
        return professors;
    }

    public Map<String, Wizard> getChosenWizardsPerUsername() {
        return chosenWizardsPerUsername;
    }

    @Override
    public void accept(ServerMessageVisitor serverMessageVisitor) {
        serverMessageVisitor.visit(this);
    }
}
