package it.polimi.ingsw.triton.launcher.model;

public class CharacterCardWithStudents extends CharacterCard{
    private Bag bag;
    private int[] students;

    public CharacterCardWithStudents(int id, int cost,Bag bag) {
        super(id, cost);
        students=new int[5];
        for(int i=0;i<4;i++) {
            students[bag.drawStudent().ordinal()]++;
        }

    }
}
