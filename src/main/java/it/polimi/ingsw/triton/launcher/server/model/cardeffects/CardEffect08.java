package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.influencestrategy.InfluenceStrategyWithEffect08;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

import java.io.Serializable;
import java.util.List;

public class CardEffect08 implements CardEffect, Serializable {
    private final List<Island> islands;
    private final Player currentPlayer;

    public CardEffect08(List<Island> islands, Player currentPlayer) {
        this.islands = islands;
        this.currentPlayer = currentPlayer;
    }

    /**
     * This method sets the island's influence strategy for each island.
     */
    @Override
    public void execute() {
        for (Island island : islands) {
            island.setInfluenceStrategy(new InfluenceStrategyWithEffect08(currentPlayer));
            // remember to reset the strategy when the turn is over
        }
    }
}
