package it.polimi.ingsw.triton.launcher.model;

public class Player {
    private String username;
    private int moveCounter = 0;
    private AssistantDeck assistantDeck;
    private AssistantCard lastPlayedCard;
    private SchoolBoard schoolBoard;

    public Player(String username){
        this.username = username;
    }

    public void setWizard(Wizard wizard){
        assistantDeck = new AssistantDeck(wizard);
    }

    public SchoolBoard getSchoolBoard(){
        // TODO implement here
        return null;
    }

    public void setSchoolBoard (TowerColor towerColor){
        this.schoolBoard = new SchoolBoard(towerColor);
    }
}
