package it.polimi.ingsw.triton.launcher.model;

public class Player {
    private String username;
    private int moveCounter = 0;
    private AssistantDeck assistantDeck;
    private AssistantCard lastPlayedCard;
    private SchoolBoard schoolBoard;

    public Player(String username, TowerColor towerColor){
        this.username = username;
        this.schoolBoard = new SchoolBoard(towerColor);
    }

    public void setChosenWizard(Wizard wizard){
        // TODO implement here
    }

    public SchoolBoard getSchoolBoard(){
        // TODO implement here
        return null;
    }
}
