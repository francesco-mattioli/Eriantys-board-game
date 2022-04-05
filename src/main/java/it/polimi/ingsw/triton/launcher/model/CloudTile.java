package it.polimi.ingsw.triton.launcher.model;

public class CloudTile {
    private int id;
    private int[] students;

    public CloudTile(int id){
        this.students = new int[5];
        this.id=id;
    }

    public void setStudents(Color x, Color y, Color z){
        students[x.ordinal()]++;
        students[y.ordinal()]++;
        students[z.ordinal()]++;
    }

    public void setStudents(Color x, Color y){
        students[x.ordinal()]++;
        students[y.ordinal()]++;
    }

    public void addStudent(Color x){
        students[x.ordinal()]++;
    }

}
