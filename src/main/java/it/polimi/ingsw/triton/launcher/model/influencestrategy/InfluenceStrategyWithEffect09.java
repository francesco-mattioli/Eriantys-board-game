package it.polimi.ingsw.triton.launcher.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.enums.Color;

public class InfluenceStrategyWithEffect09 extends InfluenceStrategy{
    private final Color color;

    public InfluenceStrategyWithEffect09(Color color){
        this.color = color;
    }

    @Override
    public int execute(Player player, Player[] professors, Player dominator, Island island) {
        int influence = 0;
        if(dominator.equals(player))
            influence+= island.getDim();
        for(int i = 0; i<professors.length; i++){
            if((professors[i].equals(player)) && i != color.ordinal()){
                influence += island.getStudents()[i];
            }
        }
        return influence;
    }
}
