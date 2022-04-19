package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect06;
import it.polimi.ingsw.triton.launcher.model.influencestrategy.InfluenceStrategyWithEffect09;

import java.util.ArrayList;

public class CardEffect09 implements CardEffect {
    private ArrayList<Island> islands;
    private Color color;

    /**
     * @param islands in the game
     * @param color that does not provide influence
     */
    public CardEffect09(ArrayList<Island> islands, Color color) {
        this.islands = islands;
        this.color = color;
    }

    /**
     * This method set the island's influence strategy for each island
     */
    @Override
    public void execute() {
        for (Island island : islands) {
            island.setInfluenceStrategy(new InfluenceStrategyWithEffect09(color));
            // remember to reset the strategy when the turn is over
        }
    }

}
