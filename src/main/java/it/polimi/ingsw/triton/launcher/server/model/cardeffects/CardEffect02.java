package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorStrategyWithEffect;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;

import java.io.Serializable;

public class CardEffect02 implements CardEffect, Serializable {
    private final ProfessorsManager professorsManager;


    public CardEffect02(ProfessorsManager professorsManager){
        this.professorsManager = professorsManager;
    }

    /**
     * This method sets the ProfessorStrategy to a new Strategy in order to calculate differently
     * which player has the Professor.
     */
    @Override
    public void execute() {
        professorsManager.setProfessorStrategy(new ProfessorStrategyWithEffect());
    }
}
