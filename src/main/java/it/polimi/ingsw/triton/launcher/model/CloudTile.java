package it.polimi.ingsw.triton.launcher.model;

public class CloudTile {
    private final int id;
    private final int[] students;

    public CloudTile(int id) {
        this.id = id;
        this.students = new int[5];
    }

    public void addStudents(Color color){
        students[color.ordinal()]++;
    }

    public int[] getStudents() {
        return students;
    }
}
