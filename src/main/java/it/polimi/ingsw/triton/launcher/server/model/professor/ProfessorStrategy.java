package it.polimi.ingsw.triton.launcher.server.model.professor;

import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;

public interface ProfessorStrategy {
   public void execute(Player player, Color color, Player[] professors);
}
