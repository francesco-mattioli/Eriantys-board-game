package it.polimi.ingsw.triton.launcher.server.model.playeractions;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;
import it.polimi.ingsw.triton.launcher.server.model.Island;
import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.utils.exceptions.IllegalClientInputException;

/**
 * Represents an action performed by the user.
 * Moves a student onto an island.
 */
public class MoveStudentOntoIsland implements Action {
    private final Color student;
    private final Island islandDestination;
    private final SchoolBoard schoolBoard;

    /**
     * @param schoolBoard the school board of the player.
     * @param student the color of student to move onto island.
     * @param islandDestination the island where to put the student.
     */
    public MoveStudentOntoIsland(SchoolBoard schoolBoard, Color student, Island islandDestination){
        this.schoolBoard = schoolBoard;
        this.student = student;
        this.islandDestination = islandDestination;
    }

    private boolean isEmptyEntrance(){
        int numStudentsEntrance = 0;
        for(int i = 0; i < schoolBoard.getEntrance().length; i++){
            numStudentsEntrance += schoolBoard.getEntrance()[i];
        }
        return numStudentsEntrance == 0;
    }

    private boolean noStudentsColorInTheEntrance(){
        return schoolBoard.getEntrance()[student.ordinal()] == 0;
    }

    /**
     * Moves the student to the island selected by the user.
     */
    @Override
    public void execute() throws IllegalClientInputException {
        if(isEmptyEntrance())
            throw new IllegalClientInputException();
        else if(noStudentsColorInTheEntrance())
            throw new IllegalClientInputException();
        else{
            schoolBoard.removeStudentFromEntrance(student);
            islandDestination.addStudent(student);
        }
    }
}
