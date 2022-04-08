package it.polimi.ingsw.triton.launcher.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.Player;

import java.util.ArrayList;

public abstract class InfluenceStrategy {
    public abstract int execute(Player player, Player[] professors, Player dominator, Island island);

}
