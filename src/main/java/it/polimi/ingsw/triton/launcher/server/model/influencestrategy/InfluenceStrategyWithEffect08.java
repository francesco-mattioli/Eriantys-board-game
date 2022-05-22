package it.polimi.ingsw.triton.launcher.server.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

public class InfluenceStrategyWithEffect08 extends InfluenceStrategyDefaultSuper {
    private final Player currentPlayer;

    public InfluenceStrategyWithEffect08(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    /**
     * This method calculate the influence between two players on an island.
     * The player candidate for the dominance has two additional influence points.
     * @param player candidate for the dominance.
     * @param professors the array with professors associated to each player.
     * @param dominator player who currently dominates the island (can be null).
     * @param island on which to calculate the influence.
     * @return the influence value.
     */
    @Override
    public int execute(Player player, Player[] professors, Player dominator, Island island) {
        if(player != null && player.equals(currentPlayer))
            return super.execute(player, professors, dominator, island) + 2;
        return super.execute(player, professors, dominator, island);
    }
}
