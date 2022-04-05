package it.polimi.ingsw.triton.launcher.model;

public class CardEffect01 implements CardEffect{
    private Bag bag;
    private Island island;


    public CardEffect01(Bag bag,Color student,Island island){
        this.bag=bag;
        this.island=island;
    }

    @Override
    public void executeEffect() {
       // TODO implement here
    }
}
