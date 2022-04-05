package it.polimi.ingsw.triton.launcher.model;

public class CloudTile {
    private int id;
    private int[] students;

    public CloudTile(int id){
        this.students = new int[5];
        this.id=id;
    }

    public void setStudents(Color x, Color y, Color z, Color w){
        students[x.ordinal()]++;
        students[y.ordinal()]++;
        students[z.ordinal()]++;
        students[w.ordinal()]++;
    }

    public void setStudents(Color x, Color y, Color z){
        students[x.ordinal()]++;
        students[y.ordinal()]++;
        students[z.ordinal()]++;
    }

    public void addStudent(Color x){
        students[x.ordinal()]++;
    }

}
