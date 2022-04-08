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
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal()){
                    if (fromDiningRoom[j] <= schoolBoard.getDiningRoom()[i]){
                        schoolBoard.getDiningRoom()[i] -= fromDiningRoom[j];
                    }
                    else throw new RuntimeException("There aren't enough students on the card!");
                }

            }
        }
    }

    public void removeStudentsFromEntrance() throws RuntimeException{
        for (int i = 0; i < schoolBoard.getEntrance().length; i++){
            for (int j = 0; j < fromEntrance.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal()){
                    if (fromEntrance[j] <= schoolBoard.getEntrance()[i]){
                        schoolBoard.getEntrance()[i] -= fromEntrance[j];
                    }
                    else throw new RuntimeException("There aren't enough students on the card!");
                }
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
