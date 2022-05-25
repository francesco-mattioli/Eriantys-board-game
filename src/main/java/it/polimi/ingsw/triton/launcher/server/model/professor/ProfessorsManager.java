package it.polimi.ingsw.triton.launcher.server.model.professor;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

import java.io.Serializable;
import java.util.List;

public class ProfessorsManager implements Serializable {
    private ProfessorStrategy professorStrategy;

    public ProfessorsManager() {
        setProfessorStrategy(new ProfessorStrategyDefault());
    }

    /**
     * Checks the professor influence when a player puts a student in his dining room.
     *
     * @param player candidate for the professor dominance.
     * @param color  of the professor to check.
     */
    public void updateProfessorsForAddInDiningRoom(Player player, Color color, Player[] professors) {
        professorStrategy.execute(player, color, professors);
    }

    /**
     * Checks the professor influence when a player removes a student from his dining room.
     *
     * @param players    the players of the game.
     * @param color      the color of the professor to check.
     * @param professors the array with professors associated to their owner.
     */
    public void updateProfessorsForRemoveFromDiningRoom(List<Player> players, Color color, Player[] professors) {
        for (Player p : players)
            professorStrategy.execute(p, color, professors);
    }

    /**
     * Set the strategy of the professor influence.
     *
     * @param professorStrategy the professor strategy to apply.
     */
    public void setProfessorStrategy(ProfessorStrategy professorStrategy) {
        this.professorStrategy = professorStrategy;
    }

    /**
     * Sets the default strategy of the professor influence.
     */
    public void resetProfessorStrategy() {
        professorStrategy = new ProfessorStrategyDefault();
    }

    public ProfessorStrategy getProfessorStrategy() {
        return professorStrategy;
    }
}
