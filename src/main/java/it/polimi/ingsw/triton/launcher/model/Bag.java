package it.polimi.ingsw.triton.launcher.model;

import it.polimi.ingsw.triton.launcher.model.enums.Color;

import java.util.NoSuchElementException;
import java.util.Random;

public class Bag {
    private final int numPlayer;
    private final int NUM_OF_STUDENTS_FOREACH_COLOR = 26;
    private final int[] students;

    public Bag(int numPlayers) {
        this.students = new int[5];
        this.numPlayer = numPlayers;
    }


    /**
     * Draw a random student from the bag.
     *
     * @return a random student
     */
    public Color drawStudent() throws NoSuchElementException {
        if(isEmpty())
            throw new NoSuchElementException("There aren't any other students");
        else {
            // generates a random number until it finds a student's color that has at least one pawn
            Random random = new Random();
            int randomIndex = random.nextInt(Color.numOfColors());
            int result = students[randomIndex];
            // if result == 0, there's no students of this color; thus it's necessary to generate another color
            while (result == 0) {
                randomIndex = random.nextInt(Color.numOfColors());
                result = students[randomIndex];
            }
            // decrements the number of the drawn student
            students[randomIndex]--;
            if(isEmpty())
                throw new NoSuchElementException("There aren't any other students");
            return Color.values()[randomIndex];
        }
    }

    /**
     * Fill the cloud tile with a number of students,
     * the number of students depends on the number of players
     *
     * @param cloudTile the cloudTile to fill
     */
    public void fillCloudTile(CloudTile cloudTile) {
        if (numPlayer == 2) {
            cloudTile.setStudents(drawStudent(), drawStudent(), drawStudent());
        }
        if (numPlayer > 2) {
            cloudTile.setStudents(drawStudent(), drawStudent(), drawStudent(), drawStudent());
        }
    }


    /**
     * Add to the bag 24 students for each color
     */
    public void fillBag() {
        for (int i = 0; i < Color.numOfColors(); i++) {
            students[i] += (NUM_OF_STUDENTS_FOREACH_COLOR - 2);
        }
    }

    /**
     * Add a student to the bag
     *
     * @param color the student to add to the bag
     */
    public void addStudent(Color color) throws IllegalArgumentException {
        if (color == null)
            throw new IllegalArgumentException("Color cannot be null");
        students[color.ordinal()]++;
    }


    /**
     * This method checks if the bag is empty
     *
     * @return true if the bag is empty, else return false
     */
    public boolean isEmpty() {
        for (int i = 0; i < Color.numOfColors(); i++) {
            if (students[i] > 0)
                return false;
        }
        return true;
    }

    /**
     * @return the array of students of the bag
     */
    public int[] getStudents() {
        return students;
    }


}
