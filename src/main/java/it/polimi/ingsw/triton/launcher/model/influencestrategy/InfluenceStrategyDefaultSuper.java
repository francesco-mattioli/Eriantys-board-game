package it.polimi.ingsw.triton.launcher.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.player.Player;


/**
 * This class contains code that is used by Default and Effect08.
 * This class is the superClass of Default and Effect08 in order to remove duplicated code.
 */
public class InfluenceStrategyDefaultSuper extends InfluenceStrategy{

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
