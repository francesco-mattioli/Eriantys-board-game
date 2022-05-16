package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyWithEffect06;

import java.io.Serializable;
import java.util.List;

public class CardEffect06 implements CardEffect, Serializable {
    private final List<Island> islands;

    public CardEffect06(List<Island> islands){
        this.islands = islands;
    }

    /**
     * This method set the island's influence strategy for each island.
     */
    @Override
    public void execute() {
        for (Island island: islands) {
            island.setInfluenceStrategy(new InfluenceStrategyWithEffect06());
            // remember to reset the strategy when the turn is over
        }
    }
}
