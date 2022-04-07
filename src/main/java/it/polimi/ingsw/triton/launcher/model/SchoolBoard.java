package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;

public class SchoolBoard {
    private TowerColor towerColor;
    private int numTowers;
    private int[] entrance;
    private int[] diningRoom;

    private final int INITIAL_NUM_TOWERS = 8;

    /**
     * @param towerColor the color of towers in the school board
     */
    public SchoolBoard(TowerColor towerColor){
        this.towerColor = towerColor;
        this.numTowers = INITIAL_NUM_TOWERS;
        this.entrance = new int[5];
        this.diningRoom = new int[5];
    }

    /**
     * @return the tower color of the school board
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * @return the number of towers in the school board
     */
    public int getNumTowers(){
        return numTowers;
    }

    public int[] getEntrance() {
        return entrance;
    }

    public int[] getDiningRoom() {
        return diningRoom;
    }

    public void moveTowerOntoIsland(int dim, Island island){
        // TODO implement here
    }

    /**
     * Remove the towers from the island and put them into the school board
     * @param dim the number of towers to add in the school board
     */
    public void moveTowerOntoSchoolBoard(int dim){
        numTowers+=dim;
    }

    /**
     * Add a student in the dining room of the school board
     * @param color the color of the student to insert
     */
    public void addStudentIntoDiningRoom(Color color){
        diningRoom[color.ordinal()]++;
    }

    /**
     * Add a student at the entrance of the school board
     * @param color the color of the student to add at the entrance
     */
    public void addStudentIntoEntrance(Color color){
        entrance[color.ordinal()]++;
    }

    /**
     * @param color the color of the students
     * @return the number of students in the dining room with
     * the color passed in parameter
     */
    public int getStudentsNumber(Color color){
        return diningRoom[color.ordinal()];
    }

    /**
     * Remove a student with a specified color from the entrance
     * @param student the color of the student to remove
     * @exception if there are no students of the specified color at the entrance
     */
    public void removeStudentFromEntrance(Color student){
        if(entrance[student.ordinal()] > 0)
            entrance[student.ordinal()]--;
        else
            throw new RuntimeException("No students of color " + student.name() + " at entrance");
    }

}
