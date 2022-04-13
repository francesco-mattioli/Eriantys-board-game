package it.polimi.ingsw.triton.launcher.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.Player;

public class InfluenceStrategyDefault extends InfluenceStrategyDefaultSuper {
    @Override
    public int execute(Player player, Player[] professors, Player dominator, Island island) {
        return super.execute(player,professors,dominator,island);
    }
}
