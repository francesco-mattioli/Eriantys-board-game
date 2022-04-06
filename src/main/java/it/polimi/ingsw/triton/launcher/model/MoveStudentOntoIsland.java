package it.polimi.ingsw.triton.launcher.model;

/**
 * Represent an action performed by the user.
 * This class moves a student onto an island
 */
public class MoveStudentOntoIsland implements Action{
    private Color student;
    private Island destination;
    private SchoolBoard schoolBoard;

    /**
     * @param schoolBoard the school board of the player
     * @param student the color of student to move onto island
     * @param destination the island where to put the student
     */
    public MoveStudentOntoIsland(SchoolBoard schoolBoard, Color student, Island destination){
        this.schoolBoard = schoolBoard;
        this.student = student;
        this.destination = destination;
    }

    /**
     * Move the student to the island selected by the user
     */
    @Override
    public void execute() {
        destination.addStudent(student);
    }
}
