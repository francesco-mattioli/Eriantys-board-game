package it.polimi.ingsw.triton.launcher.model.playeractions;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.SchoolBoard;

/**
 * Represents an action performed by the user.
 * Moves a student onto an island.
 */
public class MoveStudentOntoIsland implements Action {
    private Color student;
    private Island destination;
    private SchoolBoard schoolBoard;

    /**
     * @param schoolBoard the school board of the player.
     * @param student the color of student to move onto island.
     * @param destination the island where to put the student.
     */
    public MoveStudentOntoIsland(SchoolBoard schoolBoard, Color student, Island destination){
        this.schoolBoard = schoolBoard;
        this.student = student;
        this.destination = destination;
    }

    /**
     * Moves the student to the island selected by the user.
     */
    @Override
    public void execute() {
        schoolBoard.removeStudentFromEntrance(student);
        destination.addStudent(student);
    }
}
