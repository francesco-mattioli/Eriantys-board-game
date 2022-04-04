package it.polimi.ingsw.triton.launcher.model;

public class Player {
    private String username;
    private int moveCounter = 0;
    private AssistantDeck assistantDeck;
    private AssistantCard lastPlayedCard;

    public Player(String username, TowerColor towerColor){
        this.username = username;
    }

    public void setChosenWizard(Wizard wizard){
        // TODO implement here
    }

    public SchoolBoard getSchoolBoard(){
        // TODO implement here
        return null;
    }
}
