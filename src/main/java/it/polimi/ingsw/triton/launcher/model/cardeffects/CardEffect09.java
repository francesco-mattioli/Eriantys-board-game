package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect06;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect09;

import java.util.ArrayList;

public class CardEffect09 implements CardEffect {
    private ArrayList<Island> islands;
    private Color color;

    public CardEffect09(ArrayList<Island> islands, Color color) {
        this.islands = islands;
        this.color = color;
    }

    @Override
    public void execute() {
        for (Island island : islands) {
            island.setInfluenceStrategy(new InfluenceStrategyWithEffect09(color));
            // remember to reset the strategy when the turn is over
        }
    }

}
