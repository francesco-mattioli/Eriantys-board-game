package it.polimi.ingsw.triton.launcher.model;

public class CloudTile {
    private int id;
    private int[] students;

    public CloudTile(int id){
        this.students = new int[5];
        this.id=id;
    }

    /**
     * set the students when the game has 3 players
     * @param x,y,z,w the students to add to the cloudTile
     */
    public void setStudents(Color x, Color y, Color z, Color w){
        students[x.ordinal()]++;
        students[y.ordinal()]++;
        students[z.ordinal()]++;
        students[w.ordinal()]++;
    }

    /**
     * set the students when the game has 2 players
     * @param x,y,z the students to add to the cloudTile
     */
    public void setStudents(Color x, Color y, Color z){
        students[x.ordinal()]++;
        students[y.ordinal()]++;
        students[z.ordinal()]++;
    }

    /**
     * Add a students in the cloudTile
     * @param x the student to add to the cloudTile
     */
    public void addStudent(Color x){
        students[x.ordinal()]++;
    }

    /**
     * @return the students of the cloudtile
     */
    public int[] getStudents() {
        return students;
    }
}
