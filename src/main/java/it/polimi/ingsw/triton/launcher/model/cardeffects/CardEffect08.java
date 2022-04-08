package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect06;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect08;

import java.util.ArrayList;

public class CardEffect08 implements CardEffect {
    private ArrayList<Island> islands;

    public CardEffect08(ArrayList<Island> islands) {
        this.islands = islands;
    }

    @Override
    public void execute() {
        for (Island island : islands) {
            island.setInfluenceStrategy(new InfluenceStrategyWithEffect08());
            // remember to reset the strategy when the turn is over
        }
    }
}
