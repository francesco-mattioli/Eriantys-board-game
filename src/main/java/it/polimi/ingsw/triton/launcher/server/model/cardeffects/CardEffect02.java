package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorStrategyWithEffect;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;

public class CardEffect02 implements CardEffect{
    private final ProfessorsManager professorsManager;
    private final Player player;

    public CardEffect02(Player player,ProfessorsManager professorsManager){
        this.professorsManager = professorsManager;
        this.player=player;
    }

    /**
     * This method sets the ProfessorStrategy to a new Strategy in order to calculate differently
     * which player has the Professor.
     * Thus, it changes the Strategy and calls updateProfessors fofor every student color. In other words,
     * calls  updateProfessors for every dining table on the school board.
     */
    @Override
    public void execute() {
        professorsManager.setProfessorStrategy(new ProfessorStrategyWithEffect());
        for(Color color: Color.values())
            professorsManager.updateProfessors(player,color);
        // remember to set the strategy to default when the turn is over
    }
}
