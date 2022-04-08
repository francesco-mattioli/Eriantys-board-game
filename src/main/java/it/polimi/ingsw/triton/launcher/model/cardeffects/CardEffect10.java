package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.SchoolBoard;
import it.polimi.ingsw.triton.launcher.model.enums.Color;

public class CardEffect10 implements CardEffect{
    private int[] fromEntrance;
    private int[] fromDiningRoom;
    private SchoolBoard schoolBoard;

    public CardEffect10(int[] fromEntrance, int[] fromDiningRoom, SchoolBoard schoolBoard){
        this.fromEntrance = fromEntrance;
        this.fromDiningRoom = fromDiningRoom;
        this.schoolBoard = schoolBoard;
    }

    @Override
    public void execute() {
        removeStudentsFromDiningRoom();
        removeStudentsFromEntrance();
        addStudentsIntoDiningRoom();
        addStudentsIntoEntrance();
    }


    public void removeStudentsFromDiningRoom(){
        for (int i = 0; i < schoolBoard.getDiningRoom().length; i++){
            for (int j = 0; j < fromDiningRoom.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    schoolBoard.getDiningRoom()[i] -= fromDiningRoom[j];
            }
        }
    }

    public void removeStudentsFromEntrance(){
        for (int i = 0; i < schoolBoard.getEntrance().length; i++){
            for (int j = 0; j < fromEntrance.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    schoolBoard.getEntrance()[i] -= fromEntrance[j];
            }
        }
    }

    public void addStudentsIntoDiningRoom(){
        for (int i = 0; i < schoolBoard.getDiningRoom().length; i++){
            for (int j = 0; j < fromEntrance.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    schoolBoard.getDiningRoom()[i] += fromEntrance[j];
            }
        }
    }

    public void addStudentsIntoEntrance(){
        for (int i = 0; i < schoolBoard.getEntrance().length; i++){
            for (int j = 0; j < fromDiningRoom.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    schoolBoard.getEntrance()[i] += fromDiningRoom[j];
            }
        }
    }
}
