package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.islands.MotherNature;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.islands.IslandManager;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;

import java.io.Serializable;
import java.util.List;

public class CardEffect03 implements CardEffect, Serializable {
    private final Island islandWithEffect;
    private final IslandManager islandManager;
    private final MotherNature motherNature;
    private final List<Player> players;
    private final Player[] professors;

    /**
     * @param islandWithEffect is the chosen island where the influence must be calculated.
     * @param islandManager the manager of islands that contains useful methods to manage them.
     * @param motherNature to get the island where mother nature is on
     * @param players to be passed as a parameter for the updateInfluence method.
     * @param professors to be passed as a parameter for the updateInfluence method.
     */
    public CardEffect03(Island islandWithEffect, IslandManager islandManager, MotherNature motherNature,List<Player> players, Player[] professors){
        this.islandWithEffect = islandWithEffect;
        this.islandManager = islandManager;
        this.motherNature=motherNature;
        this.players = players;
        this.professors = professors;
    }

    /**
     * This method calls the updateInfluence method on the island chosen by the player.
     */
    @Override
    public void execute() throws EndGameException{
        islandManager.mergeNearIslands(islandWithEffect, players, professors);
    }
}
