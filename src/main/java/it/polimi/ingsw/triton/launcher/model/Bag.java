package it.polimi.ingsw.triton.launcher.model;

import java.util.NoSuchElementException;
import java.util.Random;

public class Bag {
    private int numPlayer;
    private int[] students;
    private static Bag bagInstance;

    // some some useful final variables
    private final int NUM_OF_STUDENTS_COLORS = Color.values().length;
    private final int NUM_OF_STUDENTS_FOREACH_COLOR = 26;

    private Bag(int numPlayer) {
        this.students=new int[5];
        this.numPlayer=numPlayer;
    }

    public static Bag instance(int numPlayer){
        if(bagInstance == null)
            bagInstance = new Bag(numPlayer);
        return bagInstance;
    }

    public Color drawStudent() throws NoSuchElementException{
        if(isEmpty())
            throw new NoSuchElementException("The bag is empty; a student cannot be drawn!");
        else{
            // generates a random number until it finds a student's color that has at least one pawn
            Random random = new Random();
            int randomIndex = random.nextInt(NUM_OF_STUDENTS_COLORS);
            int result=students[randomIndex];
            // if result == 0, there's no students of this color; thus it's necessary to generate another color
            while(result==0) {
                randomIndex = random.nextInt(NUM_OF_STUDENTS_COLORS);
                result = students[randomIndex];
            }
            // decrements the number of the drawn student
            students[randomIndex]--;
            return Color.values()[result];
        }
    }


    public void fillCloudTile(CloudTile cloudTile) {
        if (numPlayer == 2){
            cloudTile.setStudents(bagInstance.drawStudent(),bagInstance.drawStudent(),bagInstance.drawStudent());
        }
        if (numPlayer == 3){
            cloudTile.setStudents(bagInstance.drawStudent(),bagInstance.drawStudent(),bagInstance.drawStudent(),bagInstance.drawStudent());
        }
    }


    public void fillBag() {
        for (int i=0; i<NUM_OF_STUDENTS_COLORS; i++){
            students[i] += NUM_OF_STUDENTS_FOREACH_COLOR-2;
        }
    }


    public void addStudent(Color color) {
        students[color.ordinal()]++;
    }


    // isEmpty() returns true if there is no student in the Bag
    public boolean isEmpty(){
        for(int i=0;i<NUM_OF_STUDENTS_COLORS;i++){
            if(students[i]>0){
                return false;
            }
        }
        return true;
    }

    public int[] getStudents() {
        return students;
    }



}
