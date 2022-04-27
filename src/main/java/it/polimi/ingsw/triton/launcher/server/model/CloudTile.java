package it.polimi.ingsw.triton.launcher.server.model;

import it.polimi.ingsw.triton.launcher.server.model.enums.Color;

public class CloudTile {
    private final int id;
    private final int[] students;

    public CloudTile(int id) {
        this.students = new int[5];
        this.id = id;
    }

    public void setStudents(Color color) throws IllegalArgumentException{
        if(color == null)    //To ask if create a new error of type abort that closes the game?
            throw new IllegalArgumentException("Color not valid");
        students[color.ordinal()]++;
    }

    /**
     * Add a students in the cloudTile
     *
     * @param x the student to add to the cloudTile
     */
    public void addStudent(Color x) throws IllegalArgumentException {
        if (x == null)
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
     * @throws IllegalArgumentException if there aren't students with the specified color on the cloud tile
     */
    public void removeStudentFromCloudTile(Color student) {
        if (students[student.ordinal()] > 0)
            students[student.ordinal()]--;
        else
            throw new IllegalArgumentException("There aren't " + student.name() + " students on this cloud tile");
    }
}
