package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.Bag;
import it.polimi.ingsw.triton.launcher.model.enums.Color;
import it.polimi.ingsw.triton.launcher.model.SchoolBoard;

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
    public void execute() {
        // TODO implement here
    }
}
