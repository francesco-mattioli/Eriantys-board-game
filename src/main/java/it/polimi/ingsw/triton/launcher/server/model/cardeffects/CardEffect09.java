package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyWithEffect09;

import java.io.Serializable;
import java.util.List;

public class CardEffect09 implements CardEffect, Serializable {
    private final List<Island> islands;
    private final Color color;

    /**
     * @param islands in the game.
     * @param color that does not provide influence.
     */
    public CardEffect09(List<Island> islands, Color color) {
        this.islands = islands;
        this.color = color;
    }

    /**
     * This method sets the island's influence strategy for each island.
     */
    @Override
    public void execute() {
        for (Island island : islands) {
            island.setInfluenceStrategy(new InfluenceStrategyWithEffect09(color));
            // remember to reset the strategy when the turn is over
        }
    }

}
