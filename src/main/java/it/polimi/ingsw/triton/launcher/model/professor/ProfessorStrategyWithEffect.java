package it.polimi.ingsw.triton.launcher.model.professor;

import it.polimi.ingsw.triton.launcher.model.Player;
import it.polimi.ingsw.triton.launcher.model.enums.Color;

public class ProfessorStrategyWithEffect implements ProfessorStrategy{
    /**
     *
     * @param player that has to update its professor
     * @param color
     * @param professors
     * @throws IllegalArgumentException
     */
    @Override
    public void execute(Player player, Color color, Player[] professors) throws IllegalArgumentException{
        if (color == null){
            throw new IllegalArgumentException("Color cannot be null");
        }
        if (player == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (professors == null){
            throw new IllegalArgumentException("Professors cannot be null");
        }
        // if the is no player who owns the professor, i.e. the element of the array is null, the professor is simply assigned to the player.
        else if (professors[color.ordinal()] == null){
            professors[color.ordinal()] = player;
        }
        else {
            if(player.getSchoolBoard().getDiningRoom()[color.ordinal()] >= professors[color.ordinal()].getSchoolBoard().getDiningRoom()[color.ordinal()]){
                professors[color.ordinal()] = player;
            }
        }
    }
}
