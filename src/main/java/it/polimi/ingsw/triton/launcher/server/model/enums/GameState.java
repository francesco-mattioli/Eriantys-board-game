package it.polimi.ingsw.triton.launcher.server.model.enums;

import java.io.Serializable;

/**
 * LOGIN: includes the insert of username, number of players and tower color.
 * SETUP: includes the phase of setup before the first planning phase.
 * PLANNING_PHASE: includes the operations of the planning phase.
 * BEGIN_ACTION_PHASE: includes the movement of 3-4 students.
 * MIDDLE_ACTION_PHASE: includes the movement of mother nature.
 * END_ACTION_PHASE: includes the selection of the cloud tile.
 * END: ended game.
 */
public enum GameState implements Serializable {
    LOGIN,
    SETUP,
    PLANNING_PHASE,
    ACTION_PHASE,
    END
}
