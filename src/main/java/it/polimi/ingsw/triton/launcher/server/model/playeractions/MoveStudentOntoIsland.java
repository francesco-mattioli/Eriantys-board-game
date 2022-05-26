package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.islands.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

public class MoveStudentOntoIsland implements Action {
    private final Color student;
    private final Island islandDestination;
    private final SchoolBoard schoolBoard;

    /**
     * @param schoolBoard       the school board of the player.
     * @param student           the color of student to move onto island.
     * @param islandDestination the island where to put the student.
     */
    public MoveStudentOntoIsland(SchoolBoard schoolBoard, Color student, Island islandDestination) {
        this.schoolBoard = schoolBoard;
        this.student = student;
        this.islandDestination = islandDestination;
    }


    /**
     * @return true if the player has not any students of the specified color in his entrance, false otherwise.
     */
    private boolean noStudentsColorInTheEntrance() {
        return schoolBoard.getEntrance()[student.ordinal()] == 0;
    }

    /**
     * Moves the student to the island selected by the user.
     */
    @Override
    public void execute() throws IllegalClientInputException {
        if (schoolBoard.isEntranceEmpty())
            throw new IllegalClientInputException();
        else if (noStudentsColorInTheEntrance())
            throw new IllegalClientInputException();
        else {
            schoolBoard.removeStudentFromEntrance(student);
            islandDestination.addStudent(student);
        }
    }
}
