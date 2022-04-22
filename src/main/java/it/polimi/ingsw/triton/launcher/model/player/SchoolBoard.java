package it.polimi.ingsw.triton.launcher.model.player;

import it.polimi.ingsw.triton.launcher.model.Island;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.enums.TowerColor;
import org.w3c.dom.events.EventException;

public class SchoolBoard {
    private final int INITIAL_NUM_TOWERS;
    private final TowerColor towerColor;
    private int numTowers;
    private int[] entrance;
    private int[] diningRoom;

    /**
     * @param towerColor the color of towers in the school board.
     */
    public SchoolBoard(TowerColor towerColor, int numPlayers) {
        this.towerColor = towerColor;
        if(numPlayers == 2)
            INITIAL_NUM_TOWERS = 8;
        else
            INITIAL_NUM_TOWERS = 6;
        this.numTowers = INITIAL_NUM_TOWERS;
        this.entrance = new int[5];
        this.diningRoom = new int[5];
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public int getNumTowers() {
        return numTowers;
    }

    public int[] getEntrance() {
        return entrance;
    }

    public int[] getDiningRoom() {
        return diningRoom;
    }

    /**
     * Moves the towers from the school board to the island
     * @param dim the number of towers to move
     * @throws RuntimeException if the player has not other towers on the school board so the game is ended.
     */
    public void moveTowerOntoIsland(int dim) {
        numTowers -= dim;
        if(numTowers == 0)
            throw new RuntimeException("Game is ended");
    }

    /**
     * Removes the towers from the island and put them into the school board.
     *
     * @param dim the number of towers to add in the school board.
     */
    public void moveTowerOntoSchoolBoard(int dim) {
        numTowers += dim;
    }

    /**
     * Adds a student in the dining room of the school board.
     *
     * @param color the color of the student to insert.
     */
    public void addStudentIntoDiningRoom(Color color) {
        diningRoom[color.ordinal()]++;
    }

    /**
     * Adds a student at the entrance of the school board.
     *
     * @param color the color of the student to add at the entrance.
     */
    public void addStudentIntoEntrance(Color color) {
        entrance[color.ordinal()]++;
    }

    /**
     * @param color the color of the students.
     * @return the number of students in the dining room with
     * the color passed in parameter.
     */
    public int getStudentsNumber(Color color) {
        return diningRoom[color.ordinal()];
    }

    /**
     * Removes a student with a specified color from the entrance.
     *
     * @param student the color of the student to remove.
     * @throws RuntimeException when there are no students of the specified color at the entrance.
     */
    public void removeStudentFromEntrance(Color student) throws IllegalArgumentException {
        if (entrance[student.ordinal()] > 0)
            entrance[student.ordinal()]--;
        else
            throw new IllegalArgumentException("No students of color " + student.name() + " at entrance");
    }

}
