package it.polimi.ingsw.triton.launcher.server.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

public class InfluenceStrategyWithEffect08 extends InfluenceStrategyDefaultSuper {
    /**
     * This method calculate the influence between two players on an island
     * The player candidate for the dominance has two additional influence points
     * @param player candidate for the dominance
     * @param professors
     * @param dominator player who dominates the island
     * @param island on which to calculate the influence
     * @return influence value
     */
    @Override
    public int execute(Player player, Player[] professors, Player dominator, Island island) {
        return super.execute(player, professors, dominator, island) + 2;
    }
}
