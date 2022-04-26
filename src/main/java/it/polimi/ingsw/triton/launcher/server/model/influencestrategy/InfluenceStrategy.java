package it.polimi.ingsw.triton.launcher.server.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

public abstract class InfluenceStrategy {
    public abstract int execute(Player player, Player[] professors, Player dominator, Island island);

}
