package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect06;

import java.util.ArrayList;

public class CardEffect06 implements CardEffect{
    private ArrayList<Island> islands;

    public CardEffect06(ArrayList<Island> islands){
        this.islands = islands;
    }

    /**
     * This method set the island's influence strategy for each island
     */
    @Override
    public void execute() {
        for (Island island: islands) {
            island.setInfluenceStrategy(new InfluenceStrategyWithEffect06());
            // remember to reset the strategy when the turn is over
        }
    }
}
