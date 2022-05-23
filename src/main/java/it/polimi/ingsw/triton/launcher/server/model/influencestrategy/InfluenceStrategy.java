package it.polimi.ingsw.triton.launcher.server.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

import java.io.Serializable;

public interface InfluenceStrategy extends Serializable {
    int execute(Player player, Player[] professors, Player dominator, Island island);
}
