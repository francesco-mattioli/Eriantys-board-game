package it.polimi.ingsw.triton.launcher.server.model.influencestrategy;

import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;

public class InfluenceStrategyWithEffect09 implements InfluenceStrategy {
    private final Color color;

    public InfluenceStrategyWithEffect09(Color color){
        this.color = color;
    }

    /**
     * This method calculate the influence between two players on an island.
     * The character card 09's color has no influence during this turn.
     * @param player the candidate for the dominance.
     * @param professors the array with professors associated to each player.
     * @param dominator the player who dominates the island.
     * @param island the island on which to calculate the influence.
     * @return the influence value.
     */
    @Override
    public int execute(Player player, Player[] professors, Player dominator, Island island) {
        int influence = 0;
        if(dominator != null && dominator.equals(player))
            influence+= island.getDim();
        for(int i = 0; i<professors.length; i++){
            if((professors[i] != null && professors[i].equals(player)) && i != color.ordinal()){
                influence += island.getStudents()[i];
            }
        }
        return influence;
    }
}
