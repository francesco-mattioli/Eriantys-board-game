package it.polimi.ingsw.triton.launcher.model.professor;

import it.polimi.ingsw.triton.launcher.model.player.Player;
import it.polimi.ingsw.triton.launcher.model.enums.Color;

public interface ProfessorStrategy {
   public void execute(Player player, Color color, Player[] professors);
}
