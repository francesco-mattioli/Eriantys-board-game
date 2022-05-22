package it.polimi.ingsw.triton.launcher.server.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

import java.io.Serializable;

public abstract class InfluenceStrategy implements Serializable {
    public abstract int execute(Player player, Player[] professors, Player dominator, Island island);
}
