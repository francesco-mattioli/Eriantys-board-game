package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.SchoolBoard;

public class CardEffect07 implements CardEffect{
    private int[] fromCard;
    private int[] fromSchoolBoard;
    private SchoolBoard schoolBoard;

    public CardEffect07(int[] fromCard, int[] fromSchoolBoard, SchoolBoard schoolBoard){
        this.fromCard = fromCard;
        this.fromSchoolBoard = fromSchoolBoard;
        this.schoolBoard = schoolBoard;
    }

    @Override
    public void execute() {
        // TODO implement here
    }
}
