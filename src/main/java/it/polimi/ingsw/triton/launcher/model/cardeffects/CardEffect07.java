package it.polimi.ingsw.triton.launcher.model.cardeffects;

import it.polimi.ingsw.triton.launcher.model.SchoolBoard;
import it.polimi.ingsw.triton.launcher.model.enums.Color;

public class CardEffect07 implements CardEffect{
    private int[] fromCard;
    private int[] fromSchoolBoard;
    private SchoolBoard schoolBoard;
    private int[] studentsOnCard;

    /**
     *
     * @param studentsOnCard the six students on the card
     * @param fromCard students to take from the character card
     * @param fromSchoolBoard students to take from the entrance
     * @param schoolBoard
     */
    public CardEffect07(int[] studentsOnCard, int[] fromCard, int[] fromSchoolBoard, SchoolBoard schoolBoard){
        this.fromCard = fromCard;
        this.fromSchoolBoard = fromSchoolBoard;
        this.schoolBoard = schoolBoard;
        this.studentsOnCard = studentsOnCard;
    }

    /**
     * This method swaps a maximum of three students from the card to the entrance
     */

    @Override
    public void execute() {
        removeStudentsFromCard();
        removeStudentsFromEntrance();
        addStudentsIntoEntrance();
        addStudentsOnTheCard();
    }

    /**
     * This method remove the selected students from the character card
     * @throws RuntimeException
     */

    public void removeStudentsFromCard() throws RuntimeException{
        for (int i = 0; i < studentsOnCard.length; i++){
            for (int j = 0; j < fromCard.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal()){
                    if (fromCard[j] <= studentsOnCard[i])
                    {
                        studentsOnCard[i] -= fromCard[j];
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
            for (int j = 0; j < fromSchoolBoard.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal()){
                    if (fromSchoolBoard[j] <= schoolBoard.getEntrance()[i]){
                        schoolBoard.getEntrance()[i] -= fromSchoolBoard[j];
                    }
                    else throw new RuntimeException("There aren't enough students on the card!");
                }
            }
        }
    }

    /**
     * This method adds the students taken from the character card to the entrance
     */

    public void addStudentsIntoEntrance(){
        for (int i = 0; i < fromCard.length; i++){
            for (int j = 0; j < schoolBoard.getEntrance().length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    schoolBoard.getEntrance()[j] += fromCard[i];
            }
        }
    }

    /**
     * This method adds the students taken from the entrance to the character card
     */
    public void addStudentsOnTheCard(){
        for (int i = 0; i < fromSchoolBoard.length; i++){
            for (int j = 0; j < studentsOnCard.length; j++){
                if (Color.values()[i].ordinal() == Color.values()[j].ordinal())
                    studentsOnCard[j] += fromSchoolBoard[i];
            }
        }
    }
}
