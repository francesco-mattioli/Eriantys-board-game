package it.polimi.ingsw.triton.launcher.server.model.professor;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.player.Player;

import java.io.Serializable;

public abstract class ProfessorStrategy implements Serializable {
    abstract void execute(Player player, Color color, Player[] professors);

    protected void throwExceptionWhenParameterIsNull(Player player, Color color, Player[] professors) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (professors == null) {
            throw new IllegalArgumentException("Professors cannot be null");
        }
    }
}
