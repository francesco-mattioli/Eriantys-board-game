package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;

public class ProfessorsManager {
    private ProfessorStrategy professorStrategy;
    private final Player[] professors;

    public ProfessorsManager() {
        this.professors = new Player[5];
    }

    public void updateProfessors(Player player, Color color) {
        professorStrategy.execute(player,color,professors);
    }

    public void setProfessorStrategy(ProfessorStrategy professorStrategy) {
        this.professorStrategy = professorStrategy;
    }

    public void resetProfessorStrategy() {
        professorStrategy = new ProfessorStrategyDefault();
    }

    public Player[] getProfessors() {
        return professors;
    }
}
