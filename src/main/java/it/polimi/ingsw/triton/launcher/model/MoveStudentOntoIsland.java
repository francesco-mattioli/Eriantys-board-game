package it.polimi.ingsw.triton.launcher.model;

public class MoveStudentOntoIsland implements Action{
    private Color student;
    private Island destination;

    public MoveStudentOntoIsland(Color student, Island destination){
        this.student = student;
        this.destination = destination;
    }

    @Override
    public void execute() {
        // TODO implement here
    }
}
