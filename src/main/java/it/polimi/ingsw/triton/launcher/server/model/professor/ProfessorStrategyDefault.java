package it.polimi.ingsw.triton.launcher.server.model.professor;

import it.polimi.ingsw.triton.launcher.server.model.player.Player;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;

public class ProfessorStrategyDefault implements ProfessorStrategy{
    @Override
    public void execute(Player player, Color color, Player[] professors) throws IllegalArgumentException{
        if (color == null){
            throw new IllegalArgumentException("Color cannot be null");
        }

        else if (professors[color.ordinal()] == null){
            professors[color.ordinal()] = player;
        }

        else {
            if(player.getSchoolBoard().getDiningRoom()[color.ordinal()] > professors[color.ordinal()].getSchoolBoard().getDiningRoom()[color.ordinal()]){
                professors[color.ordinal()] = player;
            }
        }
    }
}
