package it.polimi.ingsw.triton.launcher.model;

public class CardEffect11 implements CardEffect{
    private Color student;
    private SchoolBoard schoolBoard;
    private Bag bag;

    public CardEffect11(Color student, SchoolBoard schoolBoard, Bag bag){
        this.student = student;
        this.schoolBoard = schoolBoard;
        this.bag = bag;
    }

    @Override
    public void executeEffect() {
        // TODO implement here
    }
}
