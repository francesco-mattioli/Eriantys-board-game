package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.islands.IslandManager;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;

import java.io.Serializable;
import java.util.List;

public class CardEffect03 implements CardEffect, Serializable {
    private final Island motherNaturePosition;
    private final IslandManager islandManager;
    private final List<Player> players;
    private final Player[] professors;

    /**
     * @param motherNaturePosition is the chosen island where the influence must be calculated.
     * @param islandManager the manager of islands that contains useful methods to manage them.
     * @param players to be passed as a parameter for the updateInfluence method.
     * @param professors to be passed as a parameter for the updateInfluence method.
     */
    public CardEffect03(Island motherNaturePosition, IslandManager islandManager, List<Player> players, Player[] professors){
        this.motherNaturePosition = motherNaturePosition;
        this.islandManager = islandManager;
        this.players = players;
        this.professors = professors;
    }

    /**
     * This method calls the updateInfluence method on the island chosen by the player.
     */
    @Override
    public void execute() throws EndGameException{
        islandManager.mergeNearIslands(motherNaturePosition, players, professors);
    }
}
