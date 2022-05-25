package it.polimi.ingsw.triton.launcher.server.model.professor;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

public class ProfessorStrategyDefault extends ProfessorStrategy {
    /**
     * If there is no player who owns the professor, i.e. the element of the array is null, the professor is simply assigned to the player.
     * If the number of students in dining room is greater than the number of students in
     * the other player's dining room, it assigns the professor to the current player.
     *
     * @param player     the current player.
     * @param color      the color of the professor.
     * @param professors the list of professors associated to each player.
     * @throws IllegalArgumentException if the color is null.
     */
    @Override
    public void execute(Player player, Color color, Player[] professors) throws IllegalArgumentException {
        super.throwExceptionWhenParameterIsNull(player, color, professors);
        if (professors[color.ordinal()] == null)
            professors[color.ordinal()] = player;
        else {
            if (player.getSchoolBoard().getDiningRoom()[color.ordinal()] > professors[color.ordinal()].getSchoolBoard().getDiningRoom()[color.ordinal()])
                professors[color.ordinal()] = player;
        }
    }
}
