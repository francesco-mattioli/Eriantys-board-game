package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.player.Player;

import java.util.ArrayList;

public class CardEffect03 implements CardEffect{
    private final Island island;
    private final ArrayList<Player> players;
    private Player[] professors;

    /**
     *
     * @param island is the chosen island where the influence must be calculated
     * @param players to be passed as a parameter for the updateInfluence method
     * @param professors to be passed as a parameter for the updateInfluence method
     */
    public CardEffect03(Island island, ArrayList<Player> players,Player[] professors){
        this.island = island;
        this.players = players;
    }

    /**
     * This method calls the updateInfluence method on the island chosen by the player.
     */
    @Override
    public void execute() {
        island.updateInfluence(players,professors);
    }
}
