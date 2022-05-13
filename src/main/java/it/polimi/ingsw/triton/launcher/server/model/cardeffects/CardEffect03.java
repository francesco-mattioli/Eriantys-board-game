package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.utils.exceptions.EndGameException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardEffect03 implements CardEffect, Serializable {
    private final Island island;
    private final List<Player> players;
    private Player[] professors;

    /**
     *
     * @param island is the chosen island where the influence must be calculated
     * @param players to be passed as a parameter for the updateInfluence method
     * @param professors to be passed as a parameter for the updateInfluence method
     */
    public CardEffect03(Island island, List<Player> players, Player[] professors){
        this.island = island;
        this.players = players;
        this.professors = professors;
    }

    /**
     * This method calls the updateInfluence method on the island chosen by the player.
     */
    @Override
    public void execute() throws EndGameException{
        island.updateInfluence(players,professors);
    }
}
