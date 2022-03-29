package it.polimi.ingsw.triton.launcher.model;

public class CharacterCard01 extends CharacterCard{
    private int[] students= new int[5];
    private Bag bag;

    public CharacterCard01(int id,int cost,Bag bag){
        super(id,cost);
        this.bag=bag;
        for(int i=0;i<4;i++){
            students[this.bag.drawStudent()]++;
        }
    }


    @Override
    public void cardEffect(Color student, Island island) {
        students[student.ordinal()]--;
        island.addStudent(student);
    }
}
