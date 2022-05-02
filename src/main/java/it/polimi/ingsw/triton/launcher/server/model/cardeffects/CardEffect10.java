package it.polimi.ingsw.triton.launcher.server.model.cardeffects;

import it.polimi.ingsw.triton.launcher.server.model.player.SchoolBoard;
import it.polimi.ingsw.triton.launcher.server.model.enums.Color;

public class CardEffect10 implements CardEffect{
    private final int[] fromEntrance;
    private final int[] fromDiningRoom;
    private final SchoolBoard schoolBoard;

    /**
     *
     * @param fromEntrance students to take from entrance
     * @param fromDiningRoom students to take from dining room
     * @param schoolBoard of the player who played the character card
     */
    public CardEffect10(int[] fromEntrance, int[] fromDiningRoom, SchoolBoard schoolBoard){
        this.fromEntrance = fromEntrance;
        this.fromDiningRoom = fromDiningRoom;
        this.schoolBoard = schoolBoard;
    }

    /**
     * This method swaps a maximum of three students from the dining room to the entrance
     */
    @Override
    public void execute() {
        removeStudentsFromDiningRoom();
        removeStudentsFromEntrance();
        addStudentsIntoDiningRoom();
        addStudentsIntoEntrance();
    }


    /**
     * This method remove the selected students from the dining room
     * @throws RuntimeException
     */
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


    /**
     * This method remove the selected students from the entrance
     * @throws RuntimeException
     */
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

    /**
     * This method adds the students taken from the entrance to the dining room
     */
    public void addStudentsIntoDiningRoom(){
        for (int i = 0; i < schoolBoard.getDiningRoom().length; i++){
            for (int j = 0; j < fromEntrance.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    schoolBoard.getDiningRoom()[i] += fromEntrance[j];
            }
        }
    }

    /**
     * This method adds the students taken from the dining room to the entrance
     */
    public void addStudentsIntoEntrance(){
        for (int i = 0; i < schoolBoard.getEntrance().length; i++){
            for (int j = 0; j < fromDiningRoom.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    schoolBoard.getEntrance()[i] += fromDiningRoom[j];
            }
        }
    }
}
