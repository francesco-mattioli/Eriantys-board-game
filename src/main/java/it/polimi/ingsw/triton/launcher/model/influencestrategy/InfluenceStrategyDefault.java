package it.polimi.ingsw.triton.launcher.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.Player;

import java.util.ArrayList;

public class InfluenceStrategyDefault extends InfluenceStrategy{
    @Override
    public int execute(Player player, Player[] professors, Player dominator, Island island) {
        int influence = 0;
        if(dominator.equals(player))
            influence+= island.getDim();
        for(int i = 0; i<professors.length; i++){
            if(professors[i].equals(player)){
                influence += island.getStudents()[i];
            }
        }
        return influence;
    }
}
