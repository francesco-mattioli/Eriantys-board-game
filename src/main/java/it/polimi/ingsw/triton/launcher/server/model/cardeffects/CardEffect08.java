package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyWithEffect08;

import java.util.ArrayList;

public class CardEffect08 implements CardEffect {
    private ArrayList<Island> islands;

    public CardEffect08(ArrayList<Island> islands) {
        this.islands = islands;
    }

    /**
     * This method set the island's influence strategy for each island
     */
    @Override
    public void execute() {
        for (Island island : islands) {
            island.setInfluenceStrategy(new InfluenceStrategyWithEffect08());
            // remember to reset the strategy when the turn is over
        }
    }
}
