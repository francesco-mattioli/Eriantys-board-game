package it.polimi.ingsw.triton.launcher.model;

public class CardEffect10 implements CardEffect{
    private int[] fromCard;
    private int[] fromSchoolBoard;
    private SchoolBoard schoolBoard;

    public CardEffect10(int[] fromCard, int[] fromSchoolBoard, SchoolBoard schoolBoard){
        this.fromCard = fromCard;
        this.fromSchoolBoard = fromSchoolBoard;
        this.schoolBoard = schoolBoard;
    }

    @Override
    public void executeEffect() {
        // TODO implement here
    }
}