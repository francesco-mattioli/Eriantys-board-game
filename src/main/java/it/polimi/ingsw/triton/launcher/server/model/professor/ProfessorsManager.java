package it.polimi.ingsw.triton.launcher.server.model.professor;

import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;

import java.io.Serializable;

public class ProfessorsManager implements Serializable {
    private ProfessorStrategy professorStrategy;

    public ProfessorsManager() {
        setProfessorStrategy(new ProfessorStrategyDefault());
    }

    /**
     * Checks the professor influence.
     * @param player candidate for the professor dominance.
     * @param color of the professor to check.
     */
    public void updateProfessors(Player player, Color color, Player[] professors) {
        professorStrategy.execute(player,color,professors);
    }

    /**
     * Set the strategy of the professor influence.
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
