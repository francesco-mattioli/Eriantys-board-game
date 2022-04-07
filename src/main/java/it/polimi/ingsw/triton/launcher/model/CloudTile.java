package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;

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
    public void setStudents(Color x, Color y, Color z, Color w) throws IllegalArgumentException{
        if (x == null || y == null || z == null || w == null){
            throw new IllegalArgumentException("Color cannot be null");
        }
        students[x.ordinal()]++;
        students[y.ordinal()]++;
        students[z.ordinal()]++;
        students[w.ordinal()]++;
    }

    /**
     * set the students when the game has 2 players
     * @param x,y,z the students to add to the cloudTile
     */
    public void setStudents(Color x, Color y, Color z) throws IllegalArgumentException{
        if (x == null || y == null || z == null){
            throw new IllegalArgumentException("Color cannot be null");
        }
        students[x.ordinal()]++;
        students[y.ordinal()]++;
        students[z.ordinal()]++;
    }

    /**
     * Add a students in the cloudTile
     * @param x the student to add to the cloudTile
     */
    public void addStudent(Color x) throws IllegalArgumentException{
        if(x == null)
            throw new IllegalArgumentException("Color cannot be null");
        else students[x.ordinal()]++;
    }

    /**
     * @return the students of the cloudtile
     */
    public int[] getStudents() {
        return students;
    }

    /**
     * @param student the color of the student to remove
     * @exception if there aren't students with the specified color on the cloud tile
     */
    public void removeStudentFromCloudTile(Color student){
        if(students[student.ordinal()] > 0)
            students[student.ordinal()] --;
        else
            throw new IllegalArgumentException("There aren't " + student.name() + " students on this cloud tile");
    }
}
