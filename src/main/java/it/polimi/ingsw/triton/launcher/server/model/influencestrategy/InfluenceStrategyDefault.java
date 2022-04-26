package it.polimi.ingsw.triton.launcher.server.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

public class InfluenceStrategyDefault extends InfluenceStrategyDefaultSuper {
    @Override
    public int execute(Player player, Player[] professors, Player dominator, Island island) {
        return super.execute(player,professors,dominator,island);
    }
}
