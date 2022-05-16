package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorStrategyWithEffect;
import it.polimi.ingsw.triton.launcher.server.model.professor.ProfessorsManager;

import java.io.Serializable;

public class CardEffect02 implements CardEffect, Serializable {
    private final ProfessorsManager professorsManager;
    private final Player player;

    private final Player[] professors;

    public CardEffect02(Player player,ProfessorsManager professorsManager, Player[] professors){
        this.professorsManager = professorsManager;
        this.player=player;
        this.professors = professors;
    }

    /**
     * This method sets the ProfessorStrategy to a new Strategy in order to calculate differently
     * which player has the Professor.
     * Thus, it changes the Strategy and calls updateProfessors for every student color. In other words,
     * calls  updateProfessors for every dining table on the school board.
     */
    @Override
    public void execute() {
        professorsManager.setProfessorStrategy(new ProfessorStrategyWithEffect());
        for(Color color: Color.values())
            professorsManager.updateProfessors(player,color, professors);
        // remember to set the strategy to default when the turn is over
    }
}
